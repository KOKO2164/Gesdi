package com.koko.gesdi.servicio

import com.koko.gesdi.entidad.Transaccion

data class TransactionsResponse (
    val transactionsList:List<Transaccion>
)
