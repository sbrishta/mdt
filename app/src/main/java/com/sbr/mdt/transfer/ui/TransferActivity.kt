package com.sbr.mdt.transfer.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.sbr.mdt.R
import com.sbr.mdt.databinding.ActivityTransferBinding
import com.sbr.mdt.transfer.data.payees.Payee
import com.sbr.mdt.transfer.repository.TransferRepository
import com.sbr.mdt.util.Resource
import com.sbr.mdt.util.SessionManager

class TransferActivity : AppCompatActivity() {
    private lateinit var viewModel : TransferViewModel
    private lateinit var binding : ActivityTransferBinding
    private lateinit var adapter: ArrayAdapter<Payee>
    private lateinit var selectedPayee : Payee
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spPayee = binding.spPayee
        val amount = binding.etAmount
        val description = binding.etDescription
        val makeTransfer = binding.btnMakeTransfer
        val loading =  binding.transferProgress
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        spPayee.adapter = adapter

        val token = SessionManager.fetchAuthToken()
        val repository = TransferRepository(token)
        val viewModelProviderFactory = TransferViewModelProviderFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(TransferViewModel::class.java)

        viewModel.allPayees.observe(this, Observer { response ->
            when(response) {
                is Resource.Success ->{
                    val listOfPayee = response.data?.data
                    if (listOfPayee != null) {
                        adapter.addAll(listOfPayee)
                        adapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                }
                is Resource.Loading -> {

                }
            }
        })

        viewModel.transferResponse.observe(this, Observer { response ->
            loading.visibility = View.GONE
            when(response){
                is Resource.Success ->{
                    transferSuccess()
                }
                is Resource.Error ->{
                    showTransferFailed(response.message)

                }
                is Resource.Loading ->{

                }
            }

        })
        viewModel.transferFormState.observe(this, Observer {
            val formState = it ?: return@Observer

            if (formState.amountError != null) {
                amount.error = getString(formState.amountError)
            }


        })
        viewModel.formDataChanged(amount.text.toString())
        amount.afterTextChanged {
            viewModel.formDataChanged(amount.text.toString())
        }
        spPayee.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0 : AdapterView<*>?, p1 : View?, p2 : Int, p3 : Long) {
                selectedPayee = spPayee.selectedItem as Payee
            }

            override fun onNothingSelected(p0 : AdapterView<*>?) {
            }

        }

        makeTransfer.setOnClickListener {
            startMakingTransfer(loading,amount, description)
        }

    }
    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    private fun startMakingTransfer(loading:ProgressBar,amount:TextInputEditText,description:TextInputEditText){
        loading.visibility = View.VISIBLE
        hideKeyboard((currentFocus?:this) as View)
        val amountData = amount.text.toString().toDouble()
        val desc = description.text.toString()
        viewModel.makeTransfer(amountData,desc,selectedPayee.accountNo)
    }
    private fun transferSuccess() {
        val welcome = getString(R.string.transfer_success)
            Toast . makeText (
                applicationContext,
                "$welcome ",
                Toast.LENGTH_LONG
            ).show()
    }

    private fun showTransferFailed(errorString : String?) {
        Toast.makeText(applicationContext, errorString ?: getString(R.string.transfer_failed), Toast.LENGTH_SHORT).show()
    }

    /* Extension function to simplify setting an afterTextChanged action to EditText components.
    */
    fun EditText.afterTextChanged(afterTextChanged : (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable : Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s : CharSequence, start : Int, count : Int, after : Int) {}

            override fun onTextChanged(s : CharSequence, start : Int, before : Int, count : Int) {}
        })
    }
}