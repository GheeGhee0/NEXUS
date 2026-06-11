package gheeghee.main;

import gheeghee.app.App;

public class Main {
    static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Provide .app file");
            return;
        }

        new App(args).installOrRun();
    }
}
