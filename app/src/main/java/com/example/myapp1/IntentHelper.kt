package com.example.myapp1

import android.content.Intent
import android.content.Context
import android.os.Bundle
import com.example.myapp1.Order

class IntentHelper()
{
    fun openIntent(context: Context, order : String, activityToOpen: Class<*>)
    {
        val intent = Intent(context, activityToOpen)
        intent.putExtra("order",order)
        if (context !is android.app.Activity)
        {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    fun shareIntent(context: Context, order : Order)
    {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND

        val shareOrderDetails = Bundle()
        shareOrderDetails.putString("productName", order.productName)
        shareOrderDetails.putString("customerName", order.customerName)
        shareOrderDetails.putString("customerCell", order.customerCell)

        sendIntent.putExtra(Intent.EXTRA_TEXT, shareOrderDetails)
        sendIntent.type = "text/plain"
        val shareIntent = Intent.createChooser(sendIntent, null)
        if ( context !is  android.app.Activity)
        {
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(shareIntent)
    }
}