package next.controller;

import core.annotation.web.Controller;
import core.annotation.web.RequestMapping;
import core.annotation.web.RequestMethod;
import core.db.DataBase;
import core.mvc.view.JspView;
import core.mvc.ModelAndView;
import core.mvc.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;

@Controller("/users")
public class LoginController {

    @RequestMapping("/form")
    public ModelAndView form(){
        return new ModelAndView(new JspView("/user/form.jsp"));
    }

    @RequestMapping("/loginForm")
    public ModelAndView loginForm(){
        return new ModelAndView(new JspView("/user/login.jsp"));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(String userId, String password, HttpSession httpSession){
        User user = DataBase.findUserById(userId);
        if (user == null || !user.matchPassword(password)) {
            ModelAndView modelAndView = new ModelAndView(new JspView("/user/login.jsp"));
            modelAndView.addObject("loginFailed", true);
            return modelAndView;
        }

        httpSession.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
        return new ModelAndView(new RedirectView("/"));
    }


    @RequestMapping("/logout")
    public ModelAndView logout(){
        return new ModelAndView(new RedirectView("/"));
    }

}
