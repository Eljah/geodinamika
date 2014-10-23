package geodinamika.webapp.controller;

import geodinamika.Constants;
import geodinamika.dao.SearchException;
import geodinamika.service.ActionManager;
import geodinamika.service.TaskManager;
import geodinamika.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Ilya Evlampiev on 16.10.2014.
 */
@Controller
@RequestMapping("/admin/actionlist*")
public class ActionListController {
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


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(@RequestParam(required = false, value = "q") String query) throws Exception {
        Model model = new ExtendedModelMap();
        try {
            model.addAttribute(Constants.ACTION_LIST, actionManager.search(query));
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
        }
        return new ModelAndView("admin/actionlist", model.asMap());
    }
}
