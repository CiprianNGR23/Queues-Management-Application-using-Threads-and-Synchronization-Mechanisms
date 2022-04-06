public class Main {

    public static void main(String[] args){

        GUI_APP view = new GUI_APP();

        Controller theController = new Controller(view);

        view.setVisible(true);
    }

}
