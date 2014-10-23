package geodinamika.webapp.controller;

import geodinamika.Constants;
import geodinamika.model.Action;
import geodinamika.model.steps.Step;
import geodinamika.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by Leon on 15.10.2014.
 */
@Controller
@RequestMapping("/admin/actionFromSteps*")
public class ActionController extends BaseFormController {
    private StepManager stepManager;
    private ActionManager actionManager;
    private RoleManager roleManager;
    private LookupManager lookupManager;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    public void setRoleManager(final RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    @Autowired
    public void setActionManager(final ActionManager actionManager) {
        this.actionManager = actionManager;
    }

    @Autowired
    public void setStepManager(final StepManager stepManager) {
        this.stepManager = stepManager;
    }

    @Autowired
    public void setLookupManager(final LookupManager lookupManager) {
        this.lookupManager = lookupManager;
    }

    public ActionController() {
        setCancelView("redirect:/admin/actionFromSteps");
        setSuccessView("redirect:/admin/actionlist");
    }

    @ModelAttribute("action")
    protected Action loadAction(final HttpServletRequest request) {
        final String actionId = request.getParameter("id");
        if (isFormSubmission(request) && StringUtils.isNotBlank(actionId)) {
            return actionManager.get(Long.parseLong(actionId));
        }
        return new Action();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(@ModelAttribute("action") final Action action, final BindingResult errors, final HttpServletRequest request,
                           final HttpServletResponse response)
            throws Exception {
        if (request.getParameter("cancel") != null) {
            if (!StringUtils.equals(request.getParameter("from"), "list")) {
                return getCancelView();
            } else {
                return getSuccessView();
            }
        }

        if (validator != null) { // validator is null during testing
            validator.validate(action, errors);

            if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting
                return "admin/actionFromSteps";
            }
        }

        log.debug("entering 'onSubmit' method...");

        final Locale locale = request.getLocale();

        if (request.getParameter("delete") != null) {
            actionManager.remove(action);
            saveMessage(request, getText("user.deleted", action.getActionStepsDescription(), locale));
            servletContext.setAttribute(Constants.AVAILABLE_ACTIONS, lookupManager.getAllActions());
            return getSuccessView();
        } else {

            if (request.isUserInRole(Constants.ADMIN_ROLE)) {
                final String[] actionSteps = request.getParameterValues("actionSteps");

                if (actionSteps != null) {
                    for (final String actionName : actionSteps) {
                        Step toBeAddedStep = stepManager.getStep(actionName);
                        action.addStep(toBeAddedStep);
                    }
                }

                final Integer originalVersion = action.getVersion();

                try {
                    actionManager.save(action);
                    servletContext.setAttribute(Constants.AVAILABLE_ACTIONS, lookupManager.getAllActions());

                } catch (final AccessDeniedException ade) {
                    // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
                    log.warn(ade.getMessage());
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return null;
                } catch (final Exception e) {
                    errors.rejectValue("description", "errors.existing.action",
                            new Object[]{action.getDescription(),action.getActionStepsDescription()}, "Duplicate Action Description");

                    // reset the version # to what was passed in
                    action.setVersion(originalVersion);

                    return "admin/actionFromSteps";
                }

                if (!StringUtils.equals(request.getParameter("from"), "list")) {
                    saveMessage(request, getText("user.saved", action.getActionStepsDescription(), locale));

                    // return to main Menu
                    return getCancelView();
                } else {
                    if (StringUtils.isBlank(request.getParameter("version"))) {
                        saveMessage(request, getText("user.added", action.getActionStepsDescription(), locale));

                        return getSuccessView();
                    } else {
                        saveMessage(request, getText("user.updated.byAdmin", action.getActionStepsDescription(), locale));
                    }
                }
            }

            return "admin/actionFromSteps";
        }
    }

    @ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    protected Action showForm(final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        // If not an administrator, make sure user is not trying to add or edit another user
        if (!request.isUserInRole(Constants.ADMIN_ROLE) && !isFormSubmission(request)) {
            if (isAdd(request) || request.getParameter("id") != null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                log.warn("User '" + request.getRemoteUser() + "' is trying to edit action with id '" +
                        request.getParameter("id") + "'");

                throw new AccessDeniedException("You do not have permission to modify other users.");
            }
        }

        if (!isFormSubmission(request)) {
            final String actionId = request.getParameter("id");

            Action action;
            if (actionId == null && !isAdd(request)) {
                action = new Action();
            } else if (!StringUtils.isBlank(actionId) && !"".equals(request.getParameter("version"))) {
                action = actionManager.get(Long.parseLong(actionId));
            } else {
                action = new Action();
            }
            return action;
        } else {
            // populate user object from database, so all fields don't need to be hidden fields in form
            return actionManager.get(Long.parseLong(request.getParameter("id")));
        }
    }

    private boolean isFormSubmission(final HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("post");
    }

    protected boolean isAdd(final HttpServletRequest request) {
        final String method = request.getParameter("method");
        return (method != null && method.equalsIgnoreCase("add"));
    }

}
