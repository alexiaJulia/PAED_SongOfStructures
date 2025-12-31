package RTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static RTree.Rectangle.tree_order;
import static RTree.Rectangle.underflow;

public class EliminarRTree {

    static String eliminatedPlayer;

    public static String eliminatePlayer(RTree rTree, int playerID) {
        eliminatedPlayer = null;
        Rectangle rectangleContainer = eliminatePlayerAndReturnItsContainer(rTree.root, playerID);

        Rectangle currentRectangle = rectangleContainer;

        //En cas de no haver-hi underflow, no es fa cap mena de reestructuració.
        if (currentRectangle != null) {
            while (currentRectangle.isUnderFlow()) {
                if (currentRectangle != RTree.root) {
                    Rectangle[] brothers = getBrotherRectangles(currentRectangle);
                    //Redistribució de fills amb els meus germans.
                    if (possibleExchangeWithBrothers(brothers)) {
                        exchangeWithBrothers(brothers, currentRectangle);
                        break;
                    } else {
                        //En cas de que tots els meus germans tinguin l'ordre mínim, fusionar amb algun germa i propagar l'underflow cap a munt. Revisar que pasa si arriba fins l'arrel.
                        fusionWithBrothers(brothers, currentRectangle);
                        currentRectangle = currentRectangle.getFather();
                    }
                } else {
                    if (!currentRectangle.isLeaf()) {
                        Rectangle successor = (Rectangle) currentRectangle.getChilds()[0];
                        RTree.root = successor;
                        currentRectangle.setFather(null);
                    }
                    break;
                }
            }
        }

        return eliminatedPlayer;
    }

    static boolean possibleExchangeWithBrothers(Rectangle[] brothers) {
        boolean possibleExchange = false;
        for (Rectangle brother : brothers) {
            if (brother.size > underflow) {
                possibleExchange = true;
            }
        }
        return possibleExchange;
    }

    static void exchangeWithBrothers(Rectangle[] brothers, Rectangle currentRectangle) {
        Rectangle bestBrother = null;
        Figure bestChild = null;
        Double minimPerimeterIncrement = Double.MAX_VALUE;
        Double perimeterIncrement;
        for (Rectangle brother : brothers) {
            if ((brother.size - 1) >= underflow) {
                for (Figure child : brother.getChilds()) {
                    perimeterIncrement = currentRectangle.recalculatePerimeter(child);
                    if (perimeterIncrement < minimPerimeterIncrement) {
                        minimPerimeterIncrement = perimeterIncrement;
                        bestBrother = brother;
                        bestChild = child;
                    }
                }
            }
        }
        if (bestBrother != null && bestChild != null) {
            currentRectangle.addChild(bestChild);
            currentRectangle.recalculatePerimeter();
            bestBrother.removeChild(bestChild);
            bestBrother.recalculateBounds();
        }
    }

    static void fusionWithBrothers(Rectangle[] brothers, Rectangle currentRectangle) {
        Rectangle bestBrotherToInsert = null;
        Double minimPerimeterIncrement = Double.MAX_VALUE;
        Double perimeterIncrement;
        for (Figure child : new ArrayList<>(Arrays.asList(currentRectangle.getChilds()))) {
            for (Rectangle brother : brothers) {
                perimeterIncrement = brother.recalculatePerimeter(child);
                if (brother.size < tree_order && perimeterIncrement < minimPerimeterIncrement) {
                    minimPerimeterIncrement = perimeterIncrement;
                    bestBrotherToInsert = brother;
                }
            }
            if (bestBrotherToInsert != null) {
                bestBrotherToInsert.addChild(child);
                bestBrotherToInsert.recalculatePerimeter();
                currentRectangle.removeChild(child);
                currentRectangle.recalculateBounds();
            }
        }
        if (currentRectangle.getFather() != null) {
            currentRectangle.getFather().removeChild(currentRectangle);
            currentRectangle.getFather().recalculateBounds();
        }
    }


    static Rectangle eliminatePlayerAndReturnItsContainer(Rectangle currentRectangle, int playerID) {
        Figure[] childs = currentRectangle.getChilds();

        if (!currentRectangle.isLeaf()) {
            for (Figure child : childs) {
                Rectangle result = eliminatePlayerAndReturnItsContainer((Rectangle) child, playerID);
                if (result != null) return result;
            }
        } else {
            for (Figure child : childs) {
                NodeR nodeR = (NodeR) child;
                if (nodeR.getID() == playerID) {
                    eliminatedPlayer = nodeR.getName();
                    currentRectangle.removeChild(nodeR); //Elimina el fill cercat i recalcula el perimetre del rectangle actual.
                    currentRectangle.getFather().recalculateBounds(); //Recalculant el perimetre de tots els rectangles antecessors.
                    return currentRectangle;
                }
            }
        }

        return null;
    }

    static Rectangle[] getBrotherRectangles(Rectangle current) {
        Figure[] figures = current.getFather().getChilds();
        List<Rectangle> result = new ArrayList<>();
        for (Figure f : figures) {
            if (f instanceof Rectangle) {
                result.add((Rectangle) f);
            }
        }
        return result.toArray(new Rectangle[0]);
    }
}
