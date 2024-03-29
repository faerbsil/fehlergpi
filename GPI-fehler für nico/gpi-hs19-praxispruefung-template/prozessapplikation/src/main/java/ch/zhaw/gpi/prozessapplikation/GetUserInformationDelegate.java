package ch.zhaw.gpi.prozessapplikation;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * GetUserInformationDelegate
 */
@Named("getUserInfo")
public class GetUserInformationDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execute) throws Exception {
        String userName = (String) execute.getVariable("applicant");

        String fullName; 
        String eMail; 


        RestTemplate rt= new RestTemplate(); 

        try {
            User user = rt.getForObject("http://localhost:8070/userapi/v1/users/{userName}"); 

            fullName = user.getFirstName() + " " + user.getOfficialname();
            eMail = user.geteMail();
        } catch (HttpClientErrorException e) {
            fullName = "Mr.X"; 
            eMail = "x@x.ch"; 
        
        }

        execution.setVariable("fullName", fullName);
        execution.setVariable("eMaiL", eMail);



    }

    
}