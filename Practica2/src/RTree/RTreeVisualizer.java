package RTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

public class RTreeVisualizer extends JFrame {
    public RTreeVisualizer(RTree tree) {
        setTitle("Visualización de Árbol R-Tree");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        RTreePanel drawPanel = new RTreePanel(tree.root);
        JPanel buttonLayer = new JPanel(null);
        buttonLayer.setOpaque(false);

        JPanel containerPanel = new JPanel(null);
        containerPanel.setPreferredSize(new Dimension(1200, 600));
        containerPanel.add(drawPanel);
        containerPanel.add(buttonLayer);

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        add(scrollPane);

        drawPanel.setButtonLayer(buttonLayer);
        drawPanel.setContainerPanel(containerPanel);
        drawPanel.updateSizeFromRoot();
    }

    public void start() {
        setVisible(true);
    }

    class RTreePanel extends JPanel {
        private Rectangle root;
        private int maxX, maxY;
        private JPanel buttonLayer;
        private JPanel containerPanel;

        public RTreePanel(Figure root) {
            this.root = (Rectangle) root;
            setLayout(null);
        }

        public void setButtonLayer(JPanel buttonLayer) {
            this.buttonLayer = buttonLayer;
        }

        public void setContainerPanel(JPanel panel) {
            this.containerPanel = panel;
        }

        public void updateSizeFromRoot() {
            maxX = 0;
            maxY = 0;
            computeMaxWindow(root);
            int margin = 150;

            Dimension newSize = new Dimension(maxX + margin, maxY + margin);
            setBounds(0, 0, newSize.width, newSize.height);
            buttonLayer.setBounds(0, 0, newSize.width, newSize.height);
            containerPanel.setPreferredSize(newSize);
            containerPanel.revalidate();
            containerPanel.repaint();

            addNodeButtons(root);
        }

        private void computeMaxWindow(Rectangle rect) {
            updateMaxWindow(rect.maxX, rect.maxY);
            if (rect.isLeaf()) {
                for (Figure f : rect.getChilds()) {
                    if (f instanceof NodeR node) {
                        int nodeSize = getNodeSize(node);
                        updateMaxWindow(node.getX() + nodeSize, node.getY() + nodeSize);
                    }
                }
            } else {
                for (Figure f : rect.getChilds()) {
                    computeMaxWindow((Rectangle) f);
                }
            }
        }

        private void updateMaxWindow(int x, int y) {
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
        }

        private void addNodeButtons(Rectangle figure) {
            if (figure.isLeaf()) {
                for (Figure f : figure.getChilds()) {
                    NodeR child = (NodeR) f;
                    int x = child.getX();
                    int y = child.getY();
                    int nodeSize = getNodeSize(child);
                    int flippedY = getHeight() - y - 100;

                    JButton button = new JButton();
                    button.setBounds((x + 100) - nodeSize / 2, flippedY - nodeSize / 2, nodeSize, nodeSize);
                    button.setOpaque(false);
                    button.setContentAreaFilled(false);
                    button.setBorderPainted(false);

                    button.addActionListener(e -> {
                        JOptionPane.showMessageDialog(
                                null,
                                "ID: " + child.ID +
                                        "\n(x, y) = " + "(" + child.getX() + ", " + child.getY() + ")" +
                                        "\nDate: " + child.date.replace("-", "/"),
                                child.name,
                                JOptionPane.INFORMATION_MESSAGE);
                    });

                    buttonLayer.add(button);
                }
            } else {
                for (Figure f : figure.getChilds()) {
                    addNodeButtons((Rectangle) f);
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (root != null) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(2));

                g2d.translate(0, getHeight());
                g2d.scale(1, -1);
                g2d.translate(100, 100);

                drawAxis(g2d);
                drawTree(g2d, root);
            }
        }

        private void drawAxis(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.drawLine(-100, 0, getWidth(), 0);
            g2d.drawLine(0, -100, 0, getHeight());
            AffineTransform originalTransform = g2d.getTransform();
            g2d.scale(1, -1);
            drawAxisPoints(g2d);
            g2d.setTransform(originalTransform);
            Graphics2D g2 = g2d;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            AffineTransform original = g2.getTransform();
            g2.scale(1, -1);
            g2.rotate(-Math.PI / 2);
            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.drawString("N Partides Jugades", 100, -60);
            g2.setTransform(original);
        }

        private void drawAxisPoints(Graphics2D g2d) {
            g2d.drawString("N Partides Guanyades", 100, 60);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString("100", 100, 15);
            g2d.drawString("250", 250, 15);
            g2d.drawString("500", 500, 15);
            g2d.drawString("1000", 1000, 15);
            g2d.drawString("100", -35, -100);
            g2d.drawString("250", -35, -250);
            g2d.drawString("500", -35, -500);
            g2d.drawString("1000", -35, -1000);
        }

        private void drawTree(Graphics2D g, Rectangle figure) {
            int x = figure.minX;
            int y = figure.minY;
            int width = figure.maxX - figure.minX;
            int height = figure.maxY - figure.minY;

            g.setColor(Color.RED);
            if (figure == root){
                g.setColor(Color.ORANGE);
                g.drawRect(x-1, y-1, width+2, height+2);
            } else {
                g.drawRect(x, y, width, height);
            }
            if (figure.isLeaf()) {
                for (Figure f : figure.getChilds()) {
                    NodeR child = (NodeR) f;
                    int nodeX = child.getX();
                    int nodeY = child.getY();
                    int nodeSize = getNodeSize(child);
                    setNodeColor(child, g);
                    setNodeShape(child, g, nodeX, nodeY, nodeSize);
                }
            } else {
                for (Figure f : figure.getChilds()) {
                    drawTree(g, (Rectangle) f);
                }
            }
        }

        private void setNodeShape(NodeR node, Graphics2D g, int x, int y, int nodeSize) {
            if (node.pvp) {
                g.fillOval(x - nodeSize / 2, y - nodeSize / 2, nodeSize, nodeSize);
            } else {
                g.fillRect(x - nodeSize / 2, y - nodeSize / 2, nodeSize, nodeSize);
            }
        }

        private int getNodeSize(NodeR node) {
            int year = Integer.parseInt(node.date.substring(2, 4));
            return 33 - year;
        }

        private void setNodeColor(NodeR node, Graphics2D g) {
            int red = Integer.parseInt(node.RGB_profile.substring(1, 3), 16);
            int green = Integer.parseInt(node.RGB_profile.substring(3, 5), 16);
            int blue = Integer.parseInt(node.RGB_profile.substring(5, 7), 16);
            g.setColor(new Color(red, green, blue));
        }
    }
}