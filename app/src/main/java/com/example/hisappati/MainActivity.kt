package com.example.hisappati

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private val transactions = mutableListOf<Transaction>()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var listView: ListView
    private lateinit var balanceText: TextView

    private var balance = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val titleInput = findViewById<EditText>(R.id.editTitle)
        val amountInput = findViewById<EditText>(R.id.editAmount)
        val incomeRadio = findViewById<RadioButton>(R.id.radioIncome)
        val addButton = findViewById<Button>(R.id.buttonAdd)
        listView = findViewById(R.id.transactionList)
        balanceText = findViewById(R.id.textBalance)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList())
        listView.adapter = adapter

        addButton.setOnClickListener {
            val title = titleInput.text.toString()
            val amount = amountInput.text.toString().toDoubleOrNull()
            val isIncome = incomeRadio.isChecked

            if (title.isNotEmpty() && amount != null) {
                val transaction = Transaction(title, amount, isIncome)
                transactions.add(transaction)
                updateList()
                updateBalance()
                titleInput.text.clear()
                amountInput.text.clear()
            } else {
                Toast.makeText(this, "Enter valid data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateList() {
        val items = transactions.map {
            val type = if (it.isIncome) "+" else "-"
            "$type ${it.title}: ৳${it.amount}"
        }
        adapter.clear()
        adapter.addAll(items)
    }

    private fun updateBalance() {
        balance = transactions.sumOf {
            if (it.isIncome) it.amount else -it.amount
        }
        balanceText.text = "Balance: ৳%.2f".format(balance)
    }
}