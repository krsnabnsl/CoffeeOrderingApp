package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increament(View view) {
        if(quantity == 100){
            Toast.makeText(this, "Not More than 100, Sorry !!", LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    public void decreament(View view) {
        if(quantity == 1){
            Toast.makeText(this, "Not less than 1, Sorry !!", LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    public void submitOrder(View view) {
        CheckBox whippedCreamBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream =  whippedCreamBox.isChecked();
        CheckBox chocolateBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateBox.isChecked();
        EditText nameInput = (EditText) findViewById(R.id.name_input);
        String clientNameInput = nameInput.getText().toString();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(clientNameInput, price, hasWhippedCream, hasChocolate);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + clientNameInput);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = quantity*10;
        if(hasWhippedCream){
            price += quantity*5;
        }
        if(hasChocolate){
            price += quantity*5;
        }
        return price;
    }

    private String createOrderSummary(String clientNameInput, int price, boolean hasWhippedCream, boolean hasChocolate) {
        String summary = "Name = " + clientNameInput ;
        summary += "\nAdd whipped cream? " + hasWhippedCream;
        summary += "\nAdd Chocolate? " + hasChocolate;
        summary += "\nQuantity =" + quantity;
        summary += "\nTotal = ₹" + price;
        summary += "\nThank you!";
        return summary;
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}