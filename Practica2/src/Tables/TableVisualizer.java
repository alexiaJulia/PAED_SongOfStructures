package Tables;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;


public class TableVisualizer extends JFrame {
    private int[] datos;

    public TableVisualizer(int[] datos) {
        setTitle("Histograma de Tablas");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        this.datos = datos;
        add(new HistogramaPanel());
    }

    class HistogramaPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Color[] colors = new Color[datos.length];
            colors[0] = Color.RED;
            colors[1] = Color.PINK;
            colors[2] = Color.BLUE;
            colors[3] = Color.YELLOW;
            colors[4] = Color.GREEN;
            colors[5] = Color.ORANGE;
            int ancho = getWidth();

            int alto = getHeight();
            int margen = 40;
            int numBarras = datos.length;
            int anchoBarra = (ancho - 2 * margen) / numBarras;

            int maxAltura = 0;
            for (int valor : datos) {
                if (valor > maxAltura) maxAltura = valor;
            }

            for (int i = 0; i < numBarras; i++) {
                int alturaBarra = (int) ((double) datos[i] / maxAltura * (alto - 2 * margen));
                int x = margen + i * anchoBarra;
                int y = alto - margen - alturaBarra;

                int anchoEstimado = Integer.toString(datos[i]).length() * 7; //calcula aprox cuanto ocupa un texto en pixeles
                int centroBarra = x + (anchoBarra - 8) / 2;
                int xTexto = centroBarra - anchoEstimado / 2;

                // Dibuja la barra
                g.setColor(colors[i]);
                g.fillRect(x, y, anchoBarra - 8, alturaBarra);

                // Dibuja valor arriba
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 12));
                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(datos[i]), xTexto, y - 5);

                // Dibuja el nombre
                g.setFont(new Font("Arial", Font.BOLD, 12));
                g.setColor(Color.BLACK);
                anchoEstimado = Tables.Type.values()[i].toString().length() * 7;

                xTexto = centroBarra - anchoEstimado / 2;
                g.drawString(Tables.Type.values()[i].toString(), xTexto, alto - margen + 15);

                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.PLAIN, 10));
            }
            int numDivisiones;
            if(maxAltura < 5) {
                numDivisiones = 1;
            } else{
                numDivisiones = 5;
            }
            for (int j = 0; j <= numDivisiones; j++) {
                int yPos = alto - margen - j * (alto - 2 * margen) / numDivisiones;
                int valorEtiqueta = maxAltura * j / numDivisiones;

                g.drawLine(margen - 5, yPos, margen, yPos);

                String etiqueta = String.valueOf(valorEtiqueta);
                int anchoTexto = g.getFontMetrics().stringWidth(etiqueta);
                g.drawString(etiqueta, margen - 10 - anchoTexto, yPos + 5);
            }
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 16));

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            AffineTransform original = g2.getTransform();
            g2.rotate(-Math.PI / 2);
            int yCentro = getHeight() / 2;
            int xTexto = -yCentro - g2.getFontMetrics().stringWidth("Productions") / 2;
            g2.drawString("N Productions", xTexto, margen - 50);
            g2.setTransform(original);
        }
    }
}