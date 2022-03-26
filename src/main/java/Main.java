import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    static int totalNodes = 100000;

    public static void main(String[] args) {
        TimeMeasurer timeMeasurer = new TimeMeasurer(totalNodes);
        try {
            double executeTime = timeMeasurer.executeTime();
            System.out.printf("Execute query speed: %s rows / second%n", totalNodes / executeTime);

            double prepTime = timeMeasurer.prepTime();
            System.out.printf("Prepared query time: %s rows / second%n", totalNodes / prepTime);

            double batchTime = timeMeasurer.batchTime();
            System.out.printf("batch query time: %s rows / second%n", totalNodes / batchTime);

        } catch (IOException | SQLException | XMLStreamException | JAXBException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
