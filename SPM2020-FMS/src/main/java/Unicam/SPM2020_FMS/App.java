package Unicam.SPM2020_FMS;
import Unicam.SPM2020_FMS.model.User;

/**
 * Hello world!
 *
 */
public class App 
{
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        User user = new User();
        System.out.println(user.getIdNumber());
    }

}
