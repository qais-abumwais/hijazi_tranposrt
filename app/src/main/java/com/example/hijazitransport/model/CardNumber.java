package com.example.hijazitransport.model;

public class CardNumber {
    private String CardNumber,CardCount;

    public CardNumber() {
    }

    public CardNumber(String cardNumber, String cardCount) {
        CardNumber = cardNumber;
        CardCount = cardCount;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getCardCount() {
        return CardCount;
    }

    public void setCardCount(String cardCount) {
        CardCount = cardCount;
    }
}
