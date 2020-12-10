package Unicam.SPM2020_FMS;

import java.util.stream.IntStream;

/**
 * Hello world!
 *
 */
public class App 
{
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String specHandicap="1-2";
        IntStream handicapSpotsNumbers=IntStream.of();
        String[] parts=specHandicap.split(",");
		for (String part : parts) {
			String[] spots = part.split("-");
			handicapSpotsNumbers = IntStream.concat(
					handicapSpotsNumbers, 
					IntStream.rangeClosed(Integer.parseInt(spots[0]), Integer.parseInt(spots[spots.length-1]))
			);
		}
		IntStream.rangeClosed(5, 5).forEach(a->System.out.println(a));
		System.out.println(System.getProperty("user.dir"));
		//print;
		//System.out.println(print.distinct().count());
    }

}
