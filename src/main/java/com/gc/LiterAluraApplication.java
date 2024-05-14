package com.gc;

import com.gc.menus.MenuPrincipal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LiterAluraApplication{


	public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(LiterAluraApplication.class, args);

        MenuPrincipal menuPrincipal = context.getBean(MenuPrincipal.class);
        menuPrincipal.menu();
	}
}
