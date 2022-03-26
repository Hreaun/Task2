import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import manager.Manager;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import dao.NodeDao;
import xml.StAXProcessor;

import generated.Node;

public class TimeMeasurer {

    private static String FILE = "src/main/resources/RU-NVS.osm.bz2";
    public final int totalNodes;

    public TimeMeasurer(int totalNodes) {
        this.totalNodes = totalNodes;
    }

    public double executeTime() throws IOException, SQLException, XMLStreamException, JAXBException {

        long totalTime = 0L;

        try (InputStream inputStream = new BZip2CompressorInputStream(new FileInputStream(FILE), true)) {
            try (StAXProcessor processor = new StAXProcessor(inputStream)) {
                Manager.initDB();
                NodeDao nodeDao = new NodeDao();
                for (int i = 0; i < totalNodes; i++) {
                    Node node = processor.getNextNode();

                    long startTime = System.currentTimeMillis();
                    nodeDao.saveExecute(node);
                    long endTime = System.currentTimeMillis();
                    totalTime += (endTime - startTime);
                }
            }
        }
        return (double) totalTime / 1000;
    }

    public double prepTime() throws IOException, SQLException, XMLStreamException, JAXBException {

        long totalTime = 0L;

        try (InputStream inputStream = new BZip2CompressorInputStream(new FileInputStream(FILE), true)) {
            try (StAXProcessor processor = new StAXProcessor(inputStream)) {
                Manager.initDB();
                NodeDao nodeDao = new NodeDao();
                for (int i = 0; i < totalNodes; i++) {
                    Node node = processor.getNextNode();

                    long startTime = System.currentTimeMillis();
                    nodeDao.savePrep(node);
                    long endTime = System.currentTimeMillis();
                    totalTime += (endTime - startTime);
                }
            }
        }
        return (double) totalTime / 1000;
    }

    public double batchTime() throws IOException, SQLException, XMLStreamException, JAXBException {

        long totalTime = 0L;

        try (InputStream inputStream = new BZip2CompressorInputStream(new FileInputStream(FILE), true)) {
            try (StAXProcessor processor = new StAXProcessor(inputStream)) {
                Manager.initDB();
                NodeDao nodeDao = new NodeDao();
                for (int i = 0; i < totalNodes; i++) {
                    Node node = processor.getNextNode();

                    long startTime = System.currentTimeMillis();
                    nodeDao.saveBatch(node);
                    long endTime = System.currentTimeMillis();
                    totalTime += (endTime - startTime);
                }
                long startTime = System.currentTimeMillis();
                nodeDao.flushBatch();
                long endTime = System.currentTimeMillis();
                totalTime += (endTime - startTime);
            }
        }
        return (double) totalTime / 1000;
    }

}