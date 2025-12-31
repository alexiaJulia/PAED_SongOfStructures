package Tree;

import Graf.Vertex;

import static Tree.Tree.*;

public class SpecialSends {
    public  boolean desmarkSpecialSends = false;

    public SpecialSends() {}

    public void specialSends(Node root) {
        if (!desmarkSpecialSends) {
            selectHeroesUsingPostordre(root);
            desmarkSpecialSends = true;
        } else {
            unselectHeroesUsingPreordre(root);
            desmarkSpecialSends = false;
        }

    }

    static void unselectHeroesUsingPreordre(Node currentNode) {
        currentNode.selected = false;
        if (currentNode.left != null) {
            unselectHeroesUsingPreordre(currentNode.left);
        }
        if (currentNode.right != null) {
            unselectHeroesUsingPreordre(currentNode.right);
        }
    }

    //No te fills, retorna 2, necessito ser marcat
    //1 null
    //Cal marcar 2
    //3 marcat
    static void selectHeroesUsingPostordre(Node currentNode) {
        if (currentNode.hasLeftKid()) {
            selectHeroesUsingPostordre(currentNode.left);
        }

        if (currentNode.hasRightKid()) {
            selectHeroesUsingPostordre(currentNode.right);
        }

        boolean rightNeedsMark = false;
        boolean leftNeedsMark = false;
        boolean rightDone = false;
        boolean leftDone = false;


        if (currentNode.right != null) {
            //El node de la dreta necessita d'un marcatge
            if (currentNode.right.currentSituation == 2) {
                rightNeedsMark = true;
            }
            //El node de la dreta està solucionat
            if (currentNode.right.currentSituation == 3) {
                rightDone = true;
            }
        }

        if (currentNode.left != null) {
            //El node de la esquerra necessita d'un marcatge
            if (currentNode.left.currentSituation == 2) {
                leftNeedsMark = true;
            }
            //El node de la esquerra està solucionat
            if (currentNode.left.currentSituation == 3) {
                leftDone = true;
            }
        }

        if (currentNode == Tree.root) {
            if (leftDone || rightDone) {
                currentNode.selected = false;
            } else{
                currentNode.selected = true;
            }
        }


        if (!rightNeedsMark && !rightDone && !leftNeedsMark && !leftDone) {
            currentNode.currentSituation = 2;
            return;
        }

        if (rightNeedsMark && leftNeedsMark || leftNeedsMark && !rightDone || rightNeedsMark && !leftDone) {
            currentNode.selected = true;
            currentNode.currentSituation = 3;
            return;
        }

        if (rightDone && leftDone) {
            currentNode.currentSituation = 1;
            return;
        }

        if (rightDone && !leftNeedsMark || leftDone && !rightNeedsMark) {
            currentNode.currentSituation = 1;
            return;
        }


        if (rightNeedsMark && leftDone|| leftNeedsMark && rightDone) {
            if (leftDone) {
                if (currentNode.left.hasLeftKid() && currentNode.left.hasRightKid()) {
                    currentNode.right.selected = true;
                    currentNode.right.currentSituation = 3;
                    currentNode.currentSituation = 1;
                } else {
                    if (currentNode.left.hasLeftKid()) {
                        currentNode.left.currentSituation = 1;
                        currentNode.left.selected = false;

                        currentNode.left.left.selected = true;
                        currentNode.left.left.currentSituation = 3;

                        currentNode.currentSituation = 3;
                        currentNode.selected = true;
                    }
                    if (currentNode.left.hasRightKid()) {
                        currentNode.left.currentSituation = 1;
                        currentNode.left.selected = false;

                        currentNode.left.right.selected = true;
                        currentNode.left.right.currentSituation = 3;

                        currentNode.currentSituation = 3;
                        currentNode.selected = true;
                    }
                }
            }
            if (rightDone) {
                if (currentNode.right.hasLeftKid() && currentNode.right.hasRightKid()) {
                    currentNode.left.selected = true;
                    currentNode.left.currentSituation = 3;
                    currentNode.currentSituation = 1;
                } else {
                    if (currentNode.right.hasLeftKid()) {
                        currentNode.right.currentSituation = 1;
                        currentNode.right.selected = false;

                        currentNode.right.left.selected = true;
                        currentNode.right.left.currentSituation = 3;

                        currentNode.currentSituation = 3;
                        currentNode.selected = true;
                    }
                    if (currentNode.right.hasRightKid()) {
                        currentNode.right.currentSituation = 1;
                        currentNode.right.selected = false;

                        currentNode.right.right.selected = true;
                        currentNode.right.right.currentSituation = 3;

                        currentNode.currentSituation = 3;
                        currentNode.selected = true;
                    }
                }
            }

        }

    }
}

