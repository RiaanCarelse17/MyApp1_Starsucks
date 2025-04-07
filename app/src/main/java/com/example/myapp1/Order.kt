package com.example.myapp1


class Order()
{
    lateinit var productName:String
    lateinit var customerName:String
    lateinit var customerCell:String
    lateinit var orderDate:String

    constructor(pName: String) : this()
    {
        productName = pName
    }

    constructor(pName : String, cName : String, cCell : String, oDate : String) : this(pName)
    {
        customerName = cName
        customerCell = cCell
        orderDate = oDate
    }
}