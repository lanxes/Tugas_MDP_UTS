package edu.stts.mdpweek5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText edName;
    private RadioGroup rgType;
    private RadioButton rbTea,rbCoffee,rbSmoothies;
    private CheckBox cbPearl, cbPudding,cbRedBean,cbCoconut;
    private Button btnMinus,btnPlus,btnAdd,btnEdit,btnDelete,btnReset;
    private TextView txtQty, txtTotal,txtName;
    private RecyclerView rvOrder;
    private MenuAdapter adapter;
    private ArrayList<Order> arrOrder;
    private long total = 0;
    private int index = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrOrder = new ArrayList<Order>();
        edName =(EditText)findViewById(R.id.editText_name);
        rgType = (RadioGroup) findViewById(R.id.radioGroup_type);
        rbTea = (RadioButton) findViewById(R.id.radioButton_tea);
        rbCoffee = (RadioButton) findViewById(R.id.radioButton_coffee);
        rbSmoothies = (RadioButton) findViewById(R.id.radioButton_smoothies);
        cbPearl = (CheckBox)findViewById(R.id.checkBox_pearl);
        cbPudding = (CheckBox)findViewById(R.id.checkBox_pudding);
        cbRedBean = (CheckBox)findViewById(R.id.checkBox_red_bean);
        cbCoconut = (CheckBox)findViewById(R.id.checkBox_coconut);
        btnMinus = (Button) findViewById(R.id.button_minus);
        btnPlus = (Button) findViewById(R.id.button_plus);
        txtQty =(TextView)findViewById(R.id.textView_qty);
        btnAdd = (Button) findViewById(R.id.button_add);
        btnEdit = (Button) findViewById(R.id.button_edit);
        btnDelete = (Button) findViewById(R.id.button_delete);
        btnReset = (Button) findViewById(R.id.button_reset);
        rvOrder = (RecyclerView) findViewById(R.id.recyclerview_order);
        txtName = (TextView) findViewById(R.id.textView_name);
        txtTotal = (TextView) findViewById(R.id.textView_total);

        adapter = new MenuAdapter(arrOrder, new RVClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int posisi) {
                index = posisi;
                if(arrOrder.get(index).getType().equals("Tea"))
                    rbTea.setChecked(true);
                else if(arrOrder.get(index).getType().equals("Coffee"))
                    rbCoffee.setChecked(true);
                else if(arrOrder.get(index).getType().equals("Smoothies"))
                    rbSmoothies.setChecked(true);
                cbPearl.setChecked(false);
                cbRedBean.setChecked(false);
                cbCoconut.setChecked(false);
                cbPudding.setChecked(false);
                if(arrOrder.get(index).getToppings() != null)
                {
                    for (int i=0;i<arrOrder.get(index).getToppings().size();i++)
                    {
                        if(arrOrder.get(index).getToppings().get(i).equals("Pearl"))
                            cbPearl.setChecked(true);
                        if(arrOrder.get(index).getToppings().get(i).equals("Red Bean"))
                            cbRedBean.setChecked(true);
                        if(arrOrder.get(index).getToppings().get(i).equals("Coconut Jelly"))
                            cbCoconut.setChecked(true);
                        if(arrOrder.get(index).getToppings().get(i).equals("Pudding"))
                            cbPudding.setChecked(true);
                    }
                }
                txtQty.setText(arrOrder.get(index).getQty() +"");
            }
        });
        RecyclerView.LayoutManager lm = new GridLayoutManager(MainActivity.this,1);

        rvOrder.setLayoutManager(lm);
        rvOrder.setAdapter(adapter);

        total = 0;
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(txtQty.getText().toString()) - 1;
                if(qty < 1 )
                    qty = 1;
                txtQty.setText(qty+"");
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(txtQty.getText().toString()) + 1;
                txtQty.setText(qty+"");
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edName.getText()+"" != "") {
                    int totaltamp = 0;
                    int qty = Integer.parseInt(txtQty.getText().toString());
                    int selectedId = rgType.getCheckedRadioButtonId();
                    RadioButton rdbtn = (RadioButton) findViewById(selectedId);
                    String type = rdbtn.getText() + "";
                    if (type.equals("Tea"))
                        totaltamp += 23000;
                    else if (type.equals("Coffee"))
                        totaltamp += 25000;
                    else if (type.equals("Smoothies"))
                        totaltamp += 30000;
                    ArrayList<String> topping = new ArrayList<>();
                    if (cbPearl.isChecked()) {
                        topping.add("Pearl");
                        totaltamp += 3000;
                    }
                    if (cbRedBean.isChecked()) {
                        topping.add("Red Bean");
                        totaltamp += 4000;
                    }
                    if (cbCoconut.isChecked()) {
                        topping.add("Coconut Jelly");
                        totaltamp += 4000;
                    }
                    if (cbPudding.isChecked()) {
                        topping.add("Pudding");
                        totaltamp += 3000;
                    }
                    totaltamp *= qty;
                    arrOrder.add(new Order(type, topping, qty, totaltamp));
                    total += totaltamp;
                    txtTotal.setText("Total : Rp. " + total);
                    rbTea.setChecked(true);
                    cbPudding.setChecked(false);
                    cbCoconut.setChecked(false);
                    cbRedBean.setChecked(false);
                    cbPearl.setChecked(false);
                    adapter.notifyDataSetChanged();
                    txtName.setText("Hi, "+edName.getText()+"!");
                    txtQty.setText("1");
                }
                else
                    Toast.makeText(MainActivity.this, "Field Name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edName.setText("");
                rbTea.setChecked(true);
                cbPudding.setChecked(false);
                cbCoconut.setChecked(false);
                cbRedBean.setChecked(false);
                cbPearl.setChecked(false);
                txtQty.setText("1");
                total = 0;
                txtTotal.setText("Total : Rp. 0");
                txtName.setText("Hi, Cust!");
                arrOrder.clear();
                adapter.notifyDataSetChanged();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index != -1)
                {
                    long subtotal = arrOrder.get(index).getSubtotal();
                    total -= subtotal;
                    txtTotal.setText("Total : Rp. " + total);
                    arrOrder.remove(index);
                    adapter.notifyDataSetChanged();
                    index = -1;
                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index != -1)
                {
                    total -= arrOrder.get(index).getSubtotal();
                    int totaltamp = 0;
                    int qty = Integer.parseInt(txtQty.getText().toString());
                    int selectedId = rgType.getCheckedRadioButtonId();
                    RadioButton rdbtn = (RadioButton) findViewById(selectedId);
                    String type = rdbtn.getText() + "";
                    if (type.equals("Tea"))
                        totaltamp += 23000;
                    else if (type.equals("Coffee"))
                        totaltamp += 25000;
                    else if (type.equals("Smoothies"))
                        totaltamp += 30000;
                    ArrayList<String> topping = new ArrayList<>();
                    if (cbPearl.isChecked()) {
                        topping.add("Pearl");
                        totaltamp += 3000;
                    }
                    if (cbRedBean.isChecked()) {
                        topping.add("Red Bean");
                        totaltamp += 4000;
                    }
                    if (cbCoconut.isChecked()) {
                        topping.add("Coconut Jelly");
                        totaltamp += 4000;
                    }
                    if (cbPudding.isChecked()) {
                        topping.add("Pudding");
                        totaltamp += 3000;
                    }
                    totaltamp *= qty;
                    arrOrder.get(index).setType(type);
                    arrOrder.get(index).setQty(qty);
                    arrOrder.get(index).setSubtotal(totaltamp);
                    arrOrder.get(index).setToppings(topping);
                    total += totaltamp;
                    txtTotal.setText("Total : Rp. " + total + "");
                    rbTea.setChecked(true);
                    cbPudding.setChecked(false);
                    cbCoconut.setChecked(false);
                    cbRedBean.setChecked(false);
                    cbPearl.setChecked(false);
                    adapter.notifyDataSetChanged();
                    txtName.setText(edName.getText());
                }
            }
        });
    }
}
