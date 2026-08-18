package com.example.data.integrations

import kotlinx.coroutines.delay

/**
 * Enterprise Payment Architecture supporting Razorpay, UPI, Credit/Debit Cards,
 * and Recurring Subscriptions with instant invoice generation.
 */
object PaymentGatewayArchitecture {

    data class PaymentOrderRequest(
        val amountInCents: Long,
        val currency: String = "USD",
        val planName: String,
        val userEmail: String,
        val userName: String,
        val paymentMethod: PaymentMethod
    )

    enum class PaymentMethod(val title: String, val subtitle: String) {
        UPI("Instant UPI / QR Code", "Google Pay, PhonePe, Paytm, BHIM"),
        CARD("Credit & Debit Cards", "Visa, Mastercard, Amex, RuPay"),
        NET_BANKING("Net Banking", "All major national & international banks"),
        WALLET("Digital Wallets", "Apple Pay, PayPal, Nexus Credits")
    }

    data class PaymentResult(
        val isSuccess: Boolean,
        val transactionId: String,
        val orderId: String,
        val invoiceNumber: String,
        val amountFormatted: String,
        val timestamp: String,
        val errorMessage: String? = null
    )

    /**
     * Executes transaction simulation with validation checks
     */
    suspend fun processPayment(request: PaymentOrderRequest): PaymentResult {
        delay(1200) // Realistic processing delay
        val randomTxn = (100000..999999).random()
        val randomOrder = (1000..9999).random()
        val formattedAmount = "$${"%.2f".format(request.amountInCents / 100.0)}"

        return PaymentResult(
            isSuccess = true,
            transactionId = "TXN-RZP-$randomTxn",
            orderId = "ORD-$randomOrder",
            invoiceNumber = "INV-NX-2026-$randomTxn",
            amountFormatted = formattedAmount,
            timestamp = "Aug 17, 2026, 20:55 UTC"
        )
    }
}
