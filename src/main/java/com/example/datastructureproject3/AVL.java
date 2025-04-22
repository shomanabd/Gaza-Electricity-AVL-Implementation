package com.example.datastructureproject3;

import java.lang.reflect.AnnotatedTypeVariable;
import java.lang.reflect.Field;
import java.nio.file.attribute.AclFileAttributeView;

// AVL Tree Class
public class AVL {
    private AVLTreeNode root;
    public AVL(){
        root=null;
    }

    private static StringBuilder stringBuilder;

    static long count;

    public int rootHeight(){
        return height(root);
    }




    // Return the height of a node e
    private int height( AVLTreeNode e ){
        if( e == null )
            return 0;
        return e.getHeight();
    }

    // Return The  balance factor of a node
    int getBalanceFactor(AVLTreeNode n) {
        if (n == null)
            return 0;
        return height(n.getLeft()) - height(n.getRight());
    }


    //Check whether the element is in a tree or not
    private boolean contains (int e, AVLTreeNode current)
    {
        if (current==null)
            return false; // Not found, empty tree.
        else if (e<current.getElement()) // if smaller than root.
            return contains (e,current.getLeft()); // Search left subtree
        else if (e>current.getElement()) // if larger than root.
            return contains (e,current.getRight()); // Search right subtree
        return true; // found .
    }


    // Rotate binary tree node with left child(single rotate to right)
    private AVLTreeNode rotateWithRightChild(AVLTreeNode k2){
        AVLTreeNode k1 = k2.getLeft();
        k2.setLeft(k1.getRight());
        k1.setRight(k2);
        k2.setHeight(Math.max(height(k2.getLeft()),height( k2.getRight() ))+ 1);
        k1.setHeight(Math.max(height(k1.getLeft()),k2.getHeight() )+ 1);
        return k1;
    }


    // Rotate binary tree node with right child (single rotate to left)
    private AVLTreeNode rotateWithLeftChild(AVLTreeNode k1) {
        AVLTreeNode k2 = k1.getRight();
        k1.setRight(k2.getLeft());
        k2.setLeft(k1);
        k1.setHeight(Math.max(height(k1.getLeft()), height(k1.getRight())) + 1);
        k2.setHeight(Math.max(height(k2.getRight()), k1.getHeight()) + 1);
        return k2;
    }


    /* Double rotate binary tree node: first left child with its
right child; then node k3 with new left child */
    private AVLTreeNode DoubleWithLeftChild(AVLTreeNode k3){
        k3.setLeft(rotateWithRightChild( k3.getLeft() ));
        return rotateWithLeftChild( k3 );
    }


    /*Double rotate binary tree node: first right child
with its left child; then node k1 with new right child */
    private AVLTreeNode DoubleWithRightChild(AVLTreeNode k1){
        k1.setRight(rotateWithLeftChild( k1.getRight() ));
        return rotateWithRightChild( k1 );
    }


    //Returns the node contains the given element
//    private AVLTreeNode Find(int element, AVLTreeNode current)
//    {
//        if (current == null)
//            return null;
//        if (element < current.element)
//            return Find(element, current.left);
//        else if (element > current.element)
//            return Find(element, current.right);
//        else
//            return current;
//    }


    //Returns the node contains the given element
    private AVLTreeNode Find(int element, AVLTreeNode current)
    {

        while (current!=null) {

            if (element <current.getElement())
                current= current.getLeft();
            else if (element > current.getElement())
                current= current.getRight();
            else
                return current;
        }
        return null;
    }


    public AVLTreeNode Find(int element)
    {
        return Find(element,root);
    }



    //Insert element function
    private AVLTreeNode insert (int element, AVLTreeNode current){
        if (current==null)
           return new AVLTreeNode(element); //create one node tree

        if (element<current.getElement())
                    current.setLeft(insert(element,current.getLeft()));

            else if (element>current.getElement())
                current.setRight(insert(element,current.getRight()));

            else
                return current;

        // update balance factor
        current.setHeight(Math.max(height(current.getLeft()),height(current.getRight()))+1);
        int bf = getBalanceFactor(current);


        if (bf >1){
            if(element <current.getLeft().getElement()) // left-left case
                return rotateWithRightChild(current); //

            else if (element >current.getLeft().getElement()) { // left-right case

                current.setLeft(rotateWithLeftChild(current.getLeft()));
                return rotateWithRightChild(current);
                
            }

        }

        if (bf <-1){
            if(element >current.getRight().getElement()) // right-right case
                return rotateWithLeftChild(current); //

            else if (element <current.getRight().getElement()) { // right-left case

                current.setRight(rotateWithRightChild(current.getRight()));
                return rotateWithLeftChild(current);

            }

        }

        return current;
    }


    //Insert element function
    public void insert (int element){
     root= insert(element,root);

    }


    private AVLTreeNode remove (int e, AVLTreeNode current){

        if (current==null) return null; // Item not found,Empty tree
        if (e<current.getElement())
            current.setLeft(remove(e,current.getLeft()));
        else if (e>current.getElement())
            current.setRight(remove(e,current.getRight()));
        else // found element to be deleted
            if (current.getLeft()!=null && current.getRight()!=null)// two children
            {
                /*Replace with smallest in right subtree */
                current.setElement(findMin(current.getRight()).getElement());
                current.setRight(remove(current.getElement(),current.getRight()));
            }
            else// one or zero child
                current= (current.getLeft()!=null)?current.getLeft():current.getRight();


        if(current==null)
            return current;


        // update balance factor
        current.setHeight(Math.max(height(current.getLeft()),height(current.getRight()))+1);
        int bf = getBalanceFactor(current);
        if (bf >1) {
            if (getBalanceFactor(current.getLeft()) >= 0) {// left-left case
                return rotateWithRightChild(current);

            }
            else  { // left-right case

                    current.setLeft(rotateWithLeftChild(current.getLeft()));
                    return rotateWithRightChild(current);

                }

        }

        if (bf <-1) {
            if (getBalanceFactor(current.getRight()) <= 0) {// right-right case
                return rotateWithLeftChild(current);

            }
            else  { // right-left case

                current.setRight(rotateWithRightChild(current.getRight()));
                return rotateWithLeftChild(current);

            }

        }



        return current;
    }


    public void remove (int e){
        root= remove(e,root);

    }

    private AVLTreeNode findMin (AVLTreeNode current){
        if (current==null)
            return null; //Not found,Empty tree
        else if (current.getLeft()==null)
            return current;// found at left most
        else
            return findMin(current.getLeft()); //keep going to the left
    }
    private AVLTreeNode findMax (AVLTreeNode current){
        if (current==null)
            return null; //Not found,Empty tree
        else if (current.getRight()==null)
            return current;// found at right most
        else
            return findMax(current.getRight()); //keep going to the right
    }


        // print the level of the tree
        public  void printLevel(AVLTreeNode root , int level){

        if(root==null)return;

        if(level==0){
           stringBuilder.append(root.getElement()+"  ");
        }
            printLevel(root.getLeft(),level-1);
            printLevel(root.getRight(),level-1);

        }

        public String printLevel(){

            stringBuilder =new StringBuilder();

        int level =root.getHeight();

            stringBuilder.append("print by level ");
            stringBuilder.append("\n");
            stringBuilder.append("===================================");
        for(int i=0;i<=level;++i){

            stringBuilder.append("Level " + i + ":");
            stringBuilder.append("\n");
            printLevel(root,i);
            stringBuilder.append("\n");
        }

            stringBuilder.append("===================================");
            stringBuilder.append("\n");

            stringBuilder.append("postfix :");
            stringBuilder.append("\n");
        postfix(root);
            stringBuilder.append("\n");
            stringBuilder.append("infix :");
        infix(root);
            stringBuilder.append("\n");
            stringBuilder.append("prefix :");
        prefix(root);
            stringBuilder.append("\n");


            return stringBuilder.toString();
        }



    public String printLevel(int year, int month ){

      StringBuilder r=new StringBuilder();
      stringBuilder=new StringBuilder();


        AVLTreeNode yearLocation = Find(year) ;

        // chick if the year exist or not
        if(yearLocation==null) { // year don't exist
           return null;

        }

        AVLTreeNode monthLocation= ((AVL)yearLocation.getPointer()).Find(month);

        // chick if the month exist
        if( monthLocation==null){ // month don't exist

        return null;

        }




        r.append("years ");
        r.append(("\n"));
        r.append(  printLevel());

        r.append("months ");
        r.append(("\n"));
        r.append(((AVL) yearLocation.getPointer()). printLevel());

        r.append("days ");
        r.append(("\n"));
        r.append(((AVL) monthLocation.getPointer()). printLevel());





        return r.toString();
    }



    public String printHeight(int year, int month ) {

        stringBuilder = new StringBuilder();
        stringBuilder = new StringBuilder();

        AVLTreeNode yearLocation = Find(year);

        // chick if the year exist or not
        if (yearLocation == null) { // year don't exist
            return null;

        }

        AVLTreeNode monthLocation = ((AVL) yearLocation.getPointer()).Find(month);

        // chick if the month exist
        if (monthLocation == null) { // month don't exist

            return null;

        }

        stringBuilder.append("years Height:");
        stringBuilder.append(height(root));
        stringBuilder.append(("\n"));


        stringBuilder.append("months Height:");
        stringBuilder.append(((AVL) yearLocation.getPointer()).rootHeight());
        stringBuilder.append(("\n"));

        stringBuilder.append("days months Height:");
        stringBuilder.append(((AVL) monthLocation.getPointer()).rootHeight());
        stringBuilder.append(("\n"));



        return stringBuilder.toString();

    }






    private void prefix(AVLTreeNode root){
        if (root ==null)return;

        stringBuilder.append(root.getElement()+"  ");
        prefix(root.getLeft());
        prefix(root.getRight());

    }

    private void postfix(AVLTreeNode root){
        if (root ==null)return;

        postfix(root.getLeft());
        postfix(root.getRight());
        stringBuilder.append(root.getElement()+"  ");


    }

    private void infix(AVLTreeNode root){
        if (root ==null)return;


        infix(root.getLeft());
        stringBuilder.append(root.getElement()+"  ");
        infix(root.getRight());

    }


    // the method add the day in the year node
    public boolean add(int day , int month ,int year ,ElectricityRecord record){

        AVLTreeNode yearLocation = Find(year) ;

        // chick if the year exist or not
        if(yearLocation==null) { // year don't exist
            insert(year);
            yearLocation = Find(year); // the new location
            yearLocation.setPointer(new AVL());// create the month tree

        }

        AVLTreeNode monthLocation= ((AVL)yearLocation.getPointer()).Find(month);

        // chick if the month exist
        if( monthLocation==null){ // month don't exist

            // add the month in the list
            ((AVL)yearLocation.getPointer()).insert(month);

            monthLocation = ((AVL)yearLocation.getPointer()).Find(month);

            // initialization of day tree
            monthLocation.setPointer(new AVL());

        }


        // chick if the day exist or not
        if(  monthLocation.getPointer()==null ||
                ((AVL)monthLocation.getPointer()).Find(day)==null){

            ((AVL)monthLocation.getPointer()).insert(day);

            AVLTreeNode dayLocation = ((AVL)monthLocation.getPointer()).Find(day);

            dayLocation.setPointer(record);
            return true;
        }
        else

            return false;

    }


    public boolean update(int day , int month ,int year ,ElectricityRecord record){

        AVLTreeNode yearLocation = Find(year) ;

        // chick if the year exist or not
        if(yearLocation==null)
            return false;// year don't exist



         AVLTreeNode monthLocation= ((AVL)yearLocation.getPointer()).Find(month);

        // chick if the month exist
        if( monthLocation==null)
             return false;// month don't exist


        // chick if the day exist or not
        if(  monthLocation.getPointer()==null ||
                ((AVL)monthLocation.getPointer()).Find(day)==null){

            return false;// day not exist
        }
        else

        { // day exist
            AVLTreeNode dayLocation = ((AVL)monthLocation.getPointer()).Find(day);
            dayLocation.setPointer(record);

            return true;
        }


    }


    // to search about the day if the day not exist return null
    public Object search(int day , int month ,int year ){

        AVLTreeNode yearLocation = Find(year) ;

        // chick if the year exist or not
        if(yearLocation==null)
            return null;// year don't exist



        AVLTreeNode monthLocation= ((AVL)yearLocation.getPointer()).Find(month);

        // chick if the month exist
        if( monthLocation==null)
            return null;// month don't exist


        // chick if the day exist or not
        if(  monthLocation.getPointer()==null ||
                ((AVL)monthLocation.getPointer()).Find(day)==null){

            return null;// day not exist
        }
        else

        { // day exist
            AVLTreeNode dayLocation = ((AVL)monthLocation.getPointer()).Find(day);

            return dayLocation.getPointer();
        }
    }



    // to delete the day in the list
    public  boolean remove(int day,int month,int year){

        AVLTreeNode yearLocation = Find(year) ;

        // chick if the year exist or not
        if(yearLocation==null)
            return false;// year don't exist



        AVLTreeNode monthLocation= ((AVL)yearLocation.getPointer()).Find(month);

        // chick if the month exist
        if( monthLocation==null)
            return false;// month don't exist


        // chick if the day exist or not
        if(  monthLocation.getPointer()==null ||
                ((AVL)monthLocation.getPointer()).Find(day)==null){

            return false;// day not exist
        }
        else

        { // day exist
            ((AVL)monthLocation.getPointer()).remove(day);

            return true;
        }

    }

    private void inorderYear(AVLTreeNode root, int day ){
        if (root ==null)return;


        inorderYear(root.getLeft(),day);
        ((AVL)root.getPointer()).inorderMonth(day);
        inorderYear(root.getRight(),day);

    }

    private void inorderMonth(AVLTreeNode root,int day){
        if (root ==null)return;


        inorderMonth(root.getLeft(),day);
        AVLTreeNode node = ((AVL)root.getPointer()).Find(day);
        if(node!= null){
            ElectricityRecord e =(ElectricityRecord) ((node.getPointer()));

            stringBuilder.append(e.toString() );
            stringBuilder.append("\n" );
        }
        inorderMonth(root.getRight(),day);

    }

    private void inorderMonth(int day){

    inorderMonth(root,day);
    }



    // this method to give statistic for a day
    public String dayStatistic(int day){

       stringBuilder=new StringBuilder();

        inorderYear(root,day);

        System.out.println(stringBuilder.toString());

        return stringBuilder.toString();
    }

    private void monthStatisticHelper(AVLTreeNode root, int month ){

        if (root ==null)return;

        monthStatisticHelper(root.getLeft(),month);
        AVLTreeNode node = ((AVL)root.getPointer()).Find(month);
        if(node!= null){

            ((AVL)node.getPointer()).printAllDay();

            return;
        }
        monthStatisticHelper(root.getRight(),month);

    }


    private  void printAllDay(AVLTreeNode root ){

        if (root ==null)return;

        printAllDay(root.getLeft());


        stringBuilder.append(((ElectricityRecord)(root.getPointer())).toString() );
        stringBuilder.append("\n" );

        printAllDay(root.getLeft());


    }

    private  void printAllDay( ){

        printAllDay(root);

    }


    // this method to give statistic for a month
    public String monthStatistic(int month){

       stringBuilder=new StringBuilder();

        monthStatisticHelper(root,month);

        return  stringBuilder.toString();
    }


    private void  printAllMonth(AVLTreeNode root ){

        if (root ==null)return;

        printAllMonth(root.getLeft());

            ((AVL)root.getPointer()).printAllDay();

        printAllMonth(root.getRight());


    }

    private void  printAllMonth( ){
        printAllMonth( root );
    }






    // this method to give statistic for a year
    public String yearStatistic(int year) {

        stringBuilder = new StringBuilder();

        if(Find(year)!=null){

            ((AVL)Find(year).getPointer()).printAllMonth();

            return  stringBuilder.toString();

        }else
            return  null;



    }






        private void printByLevel(int year,int month) {

        AVLTreeNode yearLocation = Find(year);

       printLevel();
        System.out.println("month of year "+year);
        ((AVL)yearLocation.getPointer()).printLevel();



    }

    // create the variable
    static float M_Israeli_Lines_MWsField ;
    static float M_Gaza_Power_Plant_MWsField ;
    static float M_Egyptian_Lines_MWsField ;
    static float M_Total_daily_Supply_available_in_MWsField;
    static  float M_Overall_demand_in_MWsField ;
    static float M_Power_Cuts_hours_day_400mgField ;
    static float M_TempField;
    static float N_Israeli_Lines_MWsField ;
    static float N_Gaza_Power_Plant_MWsField ;
    static float N_Egyptian_Lines_MWsField ;
    static float N_Total_daily_Supply_available_in_MWsField;
    static float N_Overall_demand_in_MWsField;
    static float N_Power_Cuts_hours_day_400mgField ;
    static float N_TempField ;
    static float S_Israeli_Lines_MWsField;
    static float S_Gaza_Power_Plant_MWsField;
    static float S_Egyptian_Lines_MWsField;
    static float S_Total_daily_Supply_available_in_MWsField ;
    static float S_Overall_demand_in_MWsField;
    static float S_Power_Cuts_hours_day_400mgField;
    static float S_TempField ;


    private  void totalDay(AVLTreeNode root ){


        if (root ==null)return;

        totalDay(root.getLeft());


        ElectricityRecord electricityRecord = (ElectricityRecord) (root.getPointer());

        if (M_Israeli_Lines_MWsField < electricityRecord.getIsraeliLinesMWs())
            M_Israeli_Lines_MWsField = electricityRecord.getIsraeliLinesMWs();
        if (M_Gaza_Power_Plant_MWsField < electricityRecord.getGazaPowerPlantMWs())
            M_Gaza_Power_Plant_MWsField = electricityRecord.getGazaPowerPlantMWs();
        if (M_Egyptian_Lines_MWsField < electricityRecord.getEgyptianLinesMWs())
            M_Egyptian_Lines_MWsField = electricityRecord.getEgyptianLinesMWs();
        if (M_Total_daily_Supply_available_in_MWsField < electricityRecord.getTotalDailySupplyAvailableInMWs())
            M_Total_daily_Supply_available_in_MWsField = electricityRecord.getTotalDailySupplyAvailableInMWs();
        if (M_Overall_demand_in_MWsField < electricityRecord.getOverallDemandInMWs())
            M_Overall_demand_in_MWsField = electricityRecord.getOverallDemandInMWs();
        if (M_Power_Cuts_hours_day_400mgField < electricityRecord.getPowerCutsHoursDay400mg())
            M_Power_Cuts_hours_day_400mgField = electricityRecord.getPowerCutsHoursDay400mg();
        if (M_TempField < electricityRecord.getTemp()) M_TempField = electricityRecord.getTemp();
        if (N_Israeli_Lines_MWsField > electricityRecord.getIsraeliLinesMWs())
            N_Israeli_Lines_MWsField = electricityRecord.getIsraeliLinesMWs();
        if (N_Gaza_Power_Plant_MWsField > electricityRecord.getGazaPowerPlantMWs())
            N_Gaza_Power_Plant_MWsField = electricityRecord.getGazaPowerPlantMWs();
        if (N_Egyptian_Lines_MWsField > electricityRecord.getEgyptianLinesMWs())
            N_Egyptian_Lines_MWsField = electricityRecord.getEgyptianLinesMWs();
        if (N_Total_daily_Supply_available_in_MWsField > electricityRecord.getTotalDailySupplyAvailableInMWs())
            N_Total_daily_Supply_available_in_MWsField = electricityRecord.getTotalDailySupplyAvailableInMWs();
        if (N_Overall_demand_in_MWsField > electricityRecord.getOverallDemandInMWs())
            N_Overall_demand_in_MWsField = electricityRecord.getOverallDemandInMWs();
        if (N_Power_Cuts_hours_day_400mgField > electricityRecord.getPowerCutsHoursDay400mg())
            N_Power_Cuts_hours_day_400mgField = electricityRecord.getPowerCutsHoursDay400mg();
        if ((N_TempField > electricityRecord.getTemp())) N_TempField = electricityRecord.getTemp();
        S_Israeli_Lines_MWsField += electricityRecord.getIsraeliLinesMWs();
        S_Gaza_Power_Plant_MWsField += electricityRecord.getGazaPowerPlantMWs();
        S_Egyptian_Lines_MWsField += electricityRecord.getEgyptianLinesMWs();
        S_Total_daily_Supply_available_in_MWsField += electricityRecord.getTotalDailySupplyAvailableInMWs();
        S_Overall_demand_in_MWsField += electricityRecord.getOverallDemandInMWs();
        S_Power_Cuts_hours_day_400mgField += electricityRecord.getPowerCutsHoursDay400mg();
        S_TempField += electricityRecord.getTemp();

        count++;

        totalDay(root.getRight());

    }

    private  void totalDay( ){
        totalDay( root );
    }


    private void totalYear(AVLTreeNode root ) {

        if (root ==null)return;

        totalYear(root.getLeft());

        ((AVL)root.getPointer()).totalMonth();

        totalYear(root.getRight());
    }


    // this method to give statistic for a year
    public void totalYear() {

        totalYear(root);
    }


    private void totalMonth(AVLTreeNode root ) {

        if (root ==null)return;

        totalMonth(root.getLeft());

        ((AVL)root.getPointer()).totalDay();

        totalMonth(root.getRight());
    }


    // this method to give statistic for a year
    public void totalMonth() {

        totalMonth(root);
    }



    // method to give the total statistic
   public double[] total() {
        double[] ans = new double[28];

        // create the variable
         M_Israeli_Lines_MWsField = Float.MIN_VALUE;
         M_Gaza_Power_Plant_MWsField = Float.MIN_VALUE;
         M_Egyptian_Lines_MWsField = Float.MIN_VALUE;
         M_Total_daily_Supply_available_in_MWsField =Float.MIN_VALUE;
         M_Overall_demand_in_MWsField = Float.MIN_VALUE;
         M_Power_Cuts_hours_day_400mgField = Float.MIN_VALUE;
         M_TempField = Float.MIN_VALUE;
         N_Israeli_Lines_MWsField = Float.MAX_VALUE;
         N_Gaza_Power_Plant_MWsField = Float.MAX_VALUE;
         N_Egyptian_Lines_MWsField = Float.MAX_VALUE;
         N_Total_daily_Supply_available_in_MWsField = Float.MAX_VALUE;
         N_Overall_demand_in_MWsField = Float.MAX_VALUE;
         N_Power_Cuts_hours_day_400mgField = Float.MAX_VALUE;
         N_TempField = Float.MAX_VALUE;
         S_Israeli_Lines_MWsField = 0;
         S_Gaza_Power_Plant_MWsField = 0;
         S_Egyptian_Lines_MWsField = 0;
         S_Total_daily_Supply_available_in_MWsField = 0;
         S_Overall_demand_in_MWsField = 0;
         S_Power_Cuts_hours_day_400mgField = 0;
         S_TempField = 0;

        count=0;


        totalYear(root);



        ans[0]= N_Israeli_Lines_MWsField ;
        ans[1]= N_Gaza_Power_Plant_MWsField ;
        ans[2]= N_Egyptian_Lines_MWsField ;
        ans[3]= N_Total_daily_Supply_available_in_MWsField ;
        ans[4]= N_Overall_demand_in_MWsField ;
        ans[5]= N_Power_Cuts_hours_day_400mgField ;
        ans[6]= N_TempField ;
        ans[7]= M_Israeli_Lines_MWsField ;
        ans[8]= M_Gaza_Power_Plant_MWsField ;
        ans[9]= M_Egyptian_Lines_MWsField ;
        ans[10]= M_Total_daily_Supply_available_in_MWsField ;
        ans[11]= M_Overall_demand_in_MWsField ;
        ans[12]= M_Power_Cuts_hours_day_400mgField ;
        ans[13]= M_TempField ;
        ans[14]= S_Israeli_Lines_MWsField ;
        ans[15]= S_Gaza_Power_Plant_MWsField ;
        ans[16]= S_Egyptian_Lines_MWsField ;
        ans[17]= S_Total_daily_Supply_available_in_MWsField ;
        ans[18]= S_Overall_demand_in_MWsField ;
        ans[19]= S_Power_Cuts_hours_day_400mgField ;
        ans[20]= S_TempField ;
        ans[21]= (double)S_Israeli_Lines_MWsField / count ;
        ans[22]=(double) S_Gaza_Power_Plant_MWsField /count ;
        ans[23]= (double)S_Egyptian_Lines_MWsField / count ;
        ans[24]= (double)S_Total_daily_Supply_available_in_MWsField / count ;
        ans[25]= (double)S_Overall_demand_in_MWsField /count ;
        ans[26]= (double)S_Power_Cuts_hours_day_400mgField / count ;
        ans[27]= (double)S_TempField /count ;

        return ans;
    }


    public String printAllYear( ) {
        stringBuilder=new StringBuilder();
        printAllYear(root);
        return stringBuilder.toString();

    }

    private void printAllYear(AVLTreeNode root ) {

        if (root ==null)return;

        printAllYear(root.getLeft());

        ((AVL)root.getPointer()).AllMonth();

        printAllYear(root.getRight());
    }


    private void AllMonth(AVLTreeNode root ) {

        if (root ==null)return;

       AllMonth(root.getLeft());

        ((AVL)root.getPointer()).allDay();

     AllMonth(root.getRight());
    }


    // this method to give statistic for a year
    public void AllMonth() {

        AllMonth(root);
    }


    private  void allDay(AVLTreeNode root ){


        if (root ==null)return;

        allDay(root.getLeft());


        ElectricityRecord electricityRecord = (ElectricityRecord) (root.getPointer());

        stringBuilder.append(electricityRecord.toString()+"\n")  ;


        allDay(root.getRight());

    }

    private  void allDay( ){
        allDay( root );
    }



}