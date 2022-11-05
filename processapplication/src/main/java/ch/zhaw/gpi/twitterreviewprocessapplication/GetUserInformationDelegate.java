package ch.zhaw.gpi.twitterreviewprocessapplication;

import java.util.Optional;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import ch.zhaw.gpi.twitterreviewprocessapplication.ldap.UserRepository;
import ch.zhaw.gpi.twitterreviewprocessapplication.ldap.User;





@Named ("getUserInformationAdapter")
public class GetUserInformationDelegate implements JavaDelegate {

@Autowired
private UserRepository userRepository;


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String userName = (String) execution.getVariable("anfrageStellenderBenutzer");


        String fullUserDescription;
        String eMail;

        Optional<User> user = userRepository.findById(userName);


        if(user.isPresent()) {
            User existingUser = user.get();
            eMail = existingUser.geteMail();
            fullUserDescription = existingUser.getFirstName() + " " + existingUser.getOfficialName() + " (" + existingUser.getHomeOrganization().getLongName() + ")";
        }
        else{
            eMail = "hatNicht@funktioniert";
            fullUserDescription = "Not (Worked)";
        }


        execution.setVariable("userMail", eMail);
        execution.setVariable("userFullDescription", fullUserDescription);




    }
    
}
