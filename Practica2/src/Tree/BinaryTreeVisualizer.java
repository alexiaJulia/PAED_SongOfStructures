package Tree;

import javax.swing.*;
import java.awt.*;
import java.util.*;

//A partir del seté o vuité node de l'arbre es perd visibilitat i els nodes s'ajunten
public class BinaryTreeVisualizer extends JFrame {

    private boolean isAVL;

    public BinaryTreeVisualizer(Tree tree, BinaryTreeAVL binaryTreeAVL, boolean isAVL) {
        setTitle("REPRESENTACIÓ");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        TreePanel treePanel;
        if(isAVL) {
            treePanel = new TreePanel(binaryTreeAVL.root);
        }else {
            treePanel = new TreePanel(tree.root);
        }
        this.isAVL = isAVL;
        JScrollPane scrollPane = new JScrollPane(treePanel);

        add(scrollPane);
    }

    public void start(){
        setVisible(true);
    }

    class TreePanel extends JPanel {
        private Node root;
        private int nodeSize = 40; // mida dels nodes
        private int verticalSpacing = 70; // espai entre nivells
        private int maxX = 0, maxY = 0;

        public TreePanel(Node root) {
            this.root = root;
            setPreferredSize(new Dimension(800, 600));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            maxX = 0;
            maxY = 0;

            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(2));
            drawTree(g2d, root, getWidth() / 2, 100, getWidth() / 4);

            SwingUtilities.invokeLater(this::updateSize);
        }

        private void drawTree(Graphics2D g, Node node, int x, int y, int offset) {
            if (node != null) {
                configureNodeDesing(g, node, x, y);

                if (node.left != null) {
                    int childX = x - offset;
                    int childY = y + verticalSpacing;
                    g.drawLine(x, y + nodeSize / 2, childX, childY - nodeSize / 2);
                    drawTree(g, node.left, childX, childY, offset / 2);
                }
                if (node.right != null) {
                    int childX = x + offset;
                    int childY = y + verticalSpacing;
                    g.drawLine(x, y + nodeSize / 2, childX, childY - nodeSize / 2);
                    drawTree(g, node.right, childX, childY, offset / 2);
                }
            }
        }

        private void configureNodeDesing(Graphics2D g, Node node, int x, int y) {
            maxX = (int) Math.max(maxX, (x + nodeSize)*1.05);
            maxY = (int) Math.max(maxY, y + nodeSize);
            int red, green, blue;
            if (node.selected) {
                red =  255;
                green = 0;
                blue = 0;
            } else {
                red = Integer.parseInt(node.RGB_armor.substring(1, 3), 16);
                green = Integer.parseInt(node.RGB_armor.substring(3, 5), 16);
                blue = Integer.parseInt(node.RGB_armor.substring(5, 7), 16);
            }

            //TODO HOUSE IN COLOR RED
            g.setColor(new Color(red, green, blue));
            g.fillOval(x - nodeSize / 2, y - nodeSize / 2, nodeSize, nodeSize);
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf((int) node.power / 1000) + "K", x - 10, y + 5);
            g.setColor(Color.BLACK);
        }


        private void updateSize() {
            int width;
            if(isAVL) {
                width = 1000;
            }else{
                width = Math.max(1000, maxX);
            }
            int height = Math.max(800, maxY);
            setPreferredSize(new Dimension(width, height));
            revalidate();
        }
    }
}