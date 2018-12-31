/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whipBox = (CheckBox) findViewById(R.id.iswhipped);
        CheckBox chocolateBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        EditText name_box = findViewById(R.id.name_box);
        String orderName = name_box.getText().toString();
        boolean isWhipped = whipBox.isChecked();
        boolean withChocolate = chocolateBox.isChecked();
        int price = calculatePrice(isWhipped,withChocolate);
        String orderSummary = (createOrderSummary(price, isWhipped, withChocolate, orderName));
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + orderName);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order.
     *
     * quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean withWhip, boolean withChocolate) {
        int price = quantity * 5;
        if (withWhip)
        {price=price + quantity;}
        if (withChocolate)
        {price= price + (quantity * 2); }

        return price;
    }

    private String createOrderSummary(int price, boolean isWhipped, boolean withChocolate, String orderName)

    {
        return "Name: " + orderName + "\n" +
                "Add whipped cream? " + isWhipped +
                "\nAdd chocolate? " + withChocolate +
                "\nQuantity: " + quantity + "\n" +
                "Total: $" + price + "\n" +
                "Thank you!";
    }

    public void increment(View view) {


        if (quantity == 100)
        { Toast.makeText(this, "You cannot order more than 100 cups of coffee per order.", Toast.LENGTH_SHORT).show();
            return;}
            else
            quantity = quantity+1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1)
        {
            Toast.makeText(this, "You must order at least one cup of coffee per order.", Toast.LENGTH_SHORT).show();
            return;}
        else
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

}