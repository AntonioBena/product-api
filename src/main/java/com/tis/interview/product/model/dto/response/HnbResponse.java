package com.tis.interview.product.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;


@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class HnbResponse {
    @JsonProperty("broj_tecajnice")
    private String exchangeRateNumber;
    @JsonProperty(value = "datum_primjene", required = true)
    private String applyDate;
    @JsonProperty("drzava")
    private String country;
    @JsonProperty("drzava_iso")
    private String countryIso;
    @JsonProperty("kupovni_tecaj")
    private String buyingRate;
    @JsonProperty("prodajni_tecaj")
    private String salesRate;
    @JsonProperty(value = "srednji_tecaj", required = true)
    private String middleRate;
    @JsonProperty("sifra_valute")
    private String currencyCode;
    @JsonProperty("valuta")
    private String currency;

    @Override
    public String toString() {
        return "HnbResponse{" +
                "exchangeRateNumber='" + exchangeRateNumber + '\'' +
                ", applyDate='" + applyDate + '\'' +
                ", country='" + country + '\'' +
                ", countryIso='" + countryIso + '\'' +
                ", buyingRate='" + buyingRate + '\'' +
                ", salesRate='" + salesRate + '\'' +
                ", middleRate='" + middleRate + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HnbResponse that = (HnbResponse) o;
        return Objects.equals(exchangeRateNumber, that.exchangeRateNumber) && Objects.equals(applyDate, that.applyDate) && Objects.equals(country, that.country) && Objects.equals(countryIso, that.countryIso) && Objects.equals(buyingRate, that.buyingRate) && Objects.equals(salesRate, that.salesRate) && Objects.equals(middleRate, that.middleRate) && Objects.equals(currencyCode, that.currencyCode) && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchangeRateNumber, applyDate, country, countryIso, buyingRate, salesRate, middleRate, currencyCode, currency);
    }
}