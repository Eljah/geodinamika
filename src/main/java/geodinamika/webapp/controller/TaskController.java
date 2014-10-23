package geodinamika.webapp.controller;

import geodinamika.Constants;
import geodinamika.model.Action;
import geodinamika.model.Role;
import geodinamika.model.Task;
import geodinamika.model.User;
import geodinamika.service.ActionManager;
import geodinamika.service.TaskManager;
import geodinamika.service.UserExistsException;
import geodinamika.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by Leon on 14.10.2014.
 */

@Controller
@RequestMapping("/tasks*")
public class TaskController extends BaseFormController {
    private TaskManager taskManager;
    private UserManager userManager;
    private ActionManager actionManager;

    @Autowired
    public void setTaskManager(final TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Autowired
    public void setUserManager(final UserManager userManager) {
        this.userManager = userManager;
    }

    @Autowired
    public void setActionManager(final ActionManager actionManager) {
        this.actionManager = actionManager;
    }

    public TaskController() {
        setCancelView("redirect:login");
        setSuccessView("redirect:tasks");
    }

    @ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    public Task showForm(final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        // If not an administrator, make sure user is not trying to add or edit another user
        if (/**!request.isUserInRole(Constants.ADMIN_ROLE) &&!**/isFormSubmission(request)) {
            if (request.getParameter("id") != null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                log.warn("User '" + request.getRemoteUser() + "' is trying to edit Task with id '" +
                        request.getParameter("id") + "'");

                throw new AccessDeniedException("You do not have permission to modify other users.");
            }
        }

        if (!isFormSubmission(request)) {
            final String taskId = request.getParameter("id");

            Task task;
            if (taskId == null) {
                task = new Task();
            } else if (!org.apache.commons.lang.StringUtils.isBlank(taskId) && !"".equals(request.getParameter("version"))) {
                task = taskManager.get(Long.parseLong(taskId));
                task.setId(Long.parseLong(taskId));
                task.addUser(userManager.getUserByUsername(request.getRemoteUser()));
            } else {
                task = taskManager.get(Long.parseLong(taskId));
                task.setId(Long.parseLong(taskId));
                task.addUser(userManager.getUserByUsername(request.getRemoteUser()));
            }

            return task;
        } else {
            // populate user object from database, so all fields don't need to be hidden fields in form
            return taskManager.get(Long.parseLong(request.getParameter("id")));
        }

    }

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(final Task task, final BindingResult errors, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        if (task.getId() != null) {
            task.setUsers(taskManager.get(task.getId()).getUsers());
        }
        if (request.getParameter("cancel") != null) {
            return getCancelView();
        }

        if (validator != null) { // validator is null during testing
            validator.validate(task, errors);

            if (StringUtils.isBlank(task.getDescription())) {
                errors.rejectValue("description", "errors.required", new Object[]{getText("task.description", request.getLocale())},
                        "Description is a required field.");
            }

            if (task.getAction() == null) {
                errors.rejectValue("action", "errors.required", new Object[]{getText("task.action", request.getLocale())},
                        "Please select the action. It is a required field.");
            }
            if (errors.hasErrors()) {
                return "tasks";
            }
        }

        final Locale locale = request.getLocale();


        try {
            task.addUser(userManager.getUserByUsername(request.getRemoteUser()));
            if (request.getParameter("action.description") != "" && request.getParameter("action.description") != null) {
                Action receivedAction = actionManager.getAction(request.getParameter("action.description"));
                task.setAction(receivedAction);
            }
            this.taskManager.saveTask(task);
        } catch (final AccessDeniedException ade) {
            // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
            log.warn(ade.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
            //} catch (final UserExistsException e) {
            //    errors.rejectValue("username", "errors.existing.user",
            //            new Object[] { task.getDescription() }, "duplicate user");

            ///           return "signup";
            //     }

        } catch (final UserExistsException e) {
            errors.rejectValue("description", "errors.existing.user",
                    new Object[]{task.getDescription()}, "duplicate task description");

            return "tasks";
        } catch (final Exception e) {
            // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
            log.warn(e.getMessage());
            //response.sendError(HttpServletResponse.SC_FORBIDDEN);
            errors.rejectValue("description", "errors.existing.user",
                    new Object[]{task.getDescription()}, "duplicate task description");

            return "tasks";
        }

        saveMessage(request, getText("task.message", task.getDescription(), locale));
        request.getSession().setAttribute(Constants.REGISTERED, Boolean.TRUE);

        return getSuccessView();

    }

    @ModelAttribute("id")
    protected Task loadTask(final HttpServletRequest request) {
        final String userId = request.getParameter("id");
        if (isFormSubmission(request) && org.apache.commons.lang.StringUtils.isNotBlank(userId)) {
            return taskManager.get(Long.parseLong(userId));
        }
        return new Task();
    }

    private boolean isFormSubmission(final HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("post");
    }
}


