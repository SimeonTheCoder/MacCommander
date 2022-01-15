import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
    public static Event event = Event.NONE;

    public static void main(String[] args) throws InterruptedException, IOException {
        Window window = new Window();

        window.show("Mac Commander v1.0", 1920, 1080);

        while(true) {
            TimeUnit.MILLISECONDS.sleep(50);

            window.update();

            if(!event.name().equals(Event.NONE.name())) {
                switch (event){
                    case SWAP:
                        window.swapSides();

                         break;

                    case COPY:
                        String path = window.selectDir2.getSelectedFile().getAbsolutePath();

                        path += ("\\" + window.selectDir.getSelectedFile().getName());

                        File newFile = new File(path);
                        newFile.createNewFile();

                        window.copy(window.selectDir.getSelectedFile(), newFile);

                        break;

                    case DEL:
                        File fileToBin = new File(window.selectDir.getSelectedFile().getAbsolutePath());

                        fileToBin.delete();

                        break;

                    case MOVE:
                        String pathToMoveTo = window.selectDir2.getSelectedFile().getAbsolutePath();

                        pathToMoveTo += ("\\" + window.selectDir.getSelectedFile().getName());

                        File newFileCreated = new File(pathToMoveTo);
                        newFileCreated.createNewFile();

                        window.copy(window.selectDir.getSelectedFile(), newFileCreated);

                        File fileToDel = new File(window.selectDir.getSelectedFile().getAbsolutePath());

                        fileToDel.delete();

                        break;
                }

                event = Event.NONE;
            }
        }
    }
}
