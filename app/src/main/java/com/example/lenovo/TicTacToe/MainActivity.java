package com.example.lenovo.TicTacToe;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    Button resetBT,existBT;
    TextView turnTV;
    private int size;
    TableLayout gameTL;
    char [][] data;
    char turn;
    boolean status=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_layout);
        getView();
       
    }
    public void getView(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Tic Tac Toe");
        resetBT=(Button)findViewById(R.id.resetBT);
        existBT=(Button)findViewById(R.id.existBT);
        size = Integer.parseInt("3");
        data = new char [size][size];
        gameTL = (TableLayout) findViewById(R.id.gameTL);
        turnTV = (TextView) findViewById(R.id.turnTV);
        existBT.setOnClickListener(this);
        resetBT.setOnClickListener(this);
        resetdata();
        turnTV.setText("Turn: "+turn);

        for(int i = 0; i<gameTL.getChildCount(); i++){
            TableRow row = (TableRow) gameTL.getChildAt(i);
            for(int j = 0; j<row.getChildCount(); j++){
                TextView tv = (TextView) row.getChildAt(j);
                tv.setText(R.string.none);
                tv.setOnClickListener(Move(i, j, tv));
            }
        }
    }

    protected void resetdata(){
        turn = 'X';
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                data[i][j] = ' ';
            }
        }
        for(int i = 0; i<gameTL.getChildCount(); i++){
            TableRow row = (TableRow) gameTL.getChildAt(i);
            for(int j = 0; j<row.getChildCount(); j++){
                TextView tv = (TextView) row.getChildAt(j);
                tv.setText(R.string.none);

            }
    }}

    protected int gameStatus(){

        //0 Continue
        //1 X Wins
        //2 O Wins
        //-1 Draw

        int rowX = 0, colX = 0, rowO = 0, colO = 0;
        for(int i = 0; i<size; i++){
            if(check_Row_Equality(i,'X'))
                return 1;
            if(check_Column_Equality(i, 'X'))
                return 1;
            if(check_Row_Equality(i,'O'))
                return 2;
            if(check_Column_Equality(i,'O'))
                return 2;
            if(check_Diagonal('X'))
                return 1;
            if(check_Diagonal('O'))
                return 2;
        }

        boolean dataFull = true;
        for(int i = 0; i<size; i++){
            for(int j= 0; j<size; j++){
                if(data[i][j]==' ')
                    dataFull = false;
            }
        }
        if(dataFull)
            return -1;
        else return 0;
    }

    protected boolean check_Diagonal(char player){
        int count_Equal1 = 0,count_Equal2 = 0;
        for(int i = 0; i<size; i++)
            if(data[i][i]==player)
                count_Equal1++;
        for(int i = 0; i<size; i++)
            if(data[i][size-1-i]==player)
                count_Equal2++;
        if(count_Equal1==size || count_Equal2==size)
            return true;
        else return false;
    }

    protected boolean check_Row_Equality(int r, char player){
        int count_Equal=0;
        for(int i = 0; i<size; i++){
            if(data[r][i]==player)
                count_Equal++;
        }

        if(count_Equal==size)
            return true;
        else
            return false;
    }

    protected boolean check_Column_Equality(int c, char player){
        int count_Equal=0;
        for(int i = 0; i<size; i++){
            if(data[i][c]==player)
                count_Equal++;
        }

        if(count_Equal==size)
            return true;
        else
            return false;
    }

    protected boolean Cell_Set(int r, int c){
        return !(data[r][c]==' ');
    }

    protected void stopMatch(){
        for(int i = 0; i<gameTL.getChildCount(); i++){
            TableRow row = (TableRow) gameTL.getChildAt(i);
            for(int j = 0; j<row.getChildCount(); j++){
                TextView tv = (TextView) row.getChildAt(j);
                tv.setOnClickListener(null);
            }
        }
    }

    View.OnClickListener Move(final int r, final int c, final TextView tv){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Cell_Set(r,c)) {
                    data[r][c] = turn;
                    if (turn == 'X') {
                        tv.setText(R.string.X);
                        turn = 'O';
                    } else if (turn == 'O') {
                        tv.setText(R.string.O);
                        turn = 'X';
                    }
                    if (gameStatus() == 0) {
                        turnTV.setText("Turn: " + turn);
                    }
                    else if(gameStatus() == -1){
                        turnTV.setText("Game: Draw");
                        stopMatch();
                    }
                    else{
                        turnTV.setText(turn+" Loses!");
                        stopMatch();
                    }
                }
                else{
                    if(status)
                    {
                        if(turnTV.getText().equals(turnTV.getText()+"It is already Occupied!plz choose another"))
                        {
                            status=false;
                           // turnTV.setText(turnTV.getText()+"Cell Occupied!plz choose another");
                        }
                        else {
                            turnTV.setText("");
                        turnTV.setText(turnTV.getText()+"Cell Occupied!plz choose another");}

                    }
                    else{

                        status=true;
                    }

                }
            }
        };
    }

    @Override
    public void onClick(View view) {
      if(view ==existBT)
      {
         dialogOpen();
      }
      if(view == resetBT)
      {
          Intent current = getIntent();
          finish();
          startActivity(current);
      }
    }
    public void dialogOpen(){
        final Dialog openDialog = new Dialog(this);
        openDialog.setContentView(R.layout.custom_dialog_layout);
        openDialog.setTitle(" Message");
        Toolbar toolbar=(Toolbar)openDialog.findViewById(R.id.toolbar1);
        toolbar.setTitle(" Message!");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        TextView dialog_textamountTV=(TextView)openDialog.findViewById(R.id.dialog_texttoalAmountTv1);

        Button dialogButtonYes = (Button)openDialog.findViewById(R.id.dialogButtonYes1);
        Button dialogButtonNo=(Button)openDialog.findViewById(R.id.dialogButtonNo1);
        dialogButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
                openDialog.dismiss();
            }
        });
        dialogButtonNo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // int result=cdb.updateDueAmount(new CustomerPojo(id,due_payment));
                openDialog.dismiss();
               onResume();

            }
        });
        openDialog.show();
    }

}
