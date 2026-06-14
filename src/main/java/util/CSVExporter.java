package util;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import java.io.FileWriter;

public class CSVExporter {

    public static void exportTable(
            JTable table,
            String filePath
    ) {

        try {

            TableModel model =
                    table.getModel();

            FileWriter writer =
                    new FileWriter(filePath);

            for(int i=0;i<model.getColumnCount();i++) {

                writer.append(
                        model.getColumnName(i)
                );

                if(i < model.getColumnCount()-1)
                    writer.append(",");
            }

            writer.append("\n");

            for(int row=0; row<model.getRowCount(); row++) {

                for(int col=0; col<model.getColumnCount(); col++) {

                    Object value =
                            model.getValueAt(
                                    row,
                                    col
                            );

                    writer.append(
                            String.valueOf(value)
                    );

                    if(col < model.getColumnCount()-1)
                        writer.append(",");
                }

                writer.append("\n");
            }

            writer.flush();

            writer.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}