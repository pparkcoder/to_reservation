package detailing.reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

@SpringBootApplication
public class ReservationApplication {


	public static void main(String[] args) {
		Hello hello = new Hello();
		hello.setData("hello"); 
		String data = hello.getData();
		System.out.println("data = " + data);
		SpringApplication.run(ReservationApplication.class, args);
	}
} 
