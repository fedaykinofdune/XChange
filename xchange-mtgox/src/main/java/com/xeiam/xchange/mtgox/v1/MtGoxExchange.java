/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.mtgox.v1;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.mtgox.v1.service.account.MtGoxAccountService;
import com.xeiam.xchange.mtgox.v1.service.marketdata.polling.MtGoxMarketDataService;
import com.xeiam.xchange.mtgox.v1.service.marketdata.streaming.MtGoxStreamingConfiguration;
import com.xeiam.xchange.mtgox.v1.service.marketdata.streaming.MtGoxWebsocketMarketDataService;
import com.xeiam.xchange.mtgox.v1.service.trade.polling.MtGoxTradeService;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the MtGox exchange API</li>
 * </ul>
 * <p>
 * 
 * @deprecated Use V2! This will be removed in 1.8.0+
 */
@Deprecated
public class MtGoxExchange extends BaseExchange implements Exchange {

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    // Configure the basic services if configuration does not apply
    this.pollingMarketDataService = new MtGoxMarketDataService(exchangeSpecification);
    this.pollingTradeService = new MtGoxTradeService(exchangeSpecification);
    this.pollingAccountService = new MtGoxAccountService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://data.mtgox.com");
    exchangeSpecification.setPlainTextUri("http://data.mtgox.com");
    exchangeSpecification.setHost("mtgox.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("MtGox");
    exchangeSpecification.setExchangeDescription("MtGox is a Bitcoin exchange registered in Japan.");

    return exchangeSpecification;
  }

  @Override
  public StreamingExchangeService getStreamingExchangeService(ExchangeStreamingConfiguration configuration) {

    if (configuration instanceof MtGoxStreamingConfiguration) {
      return new MtGoxWebsocketMarketDataService(getExchangeSpecification(), (MtGoxStreamingConfiguration) configuration);
    }

    throw new IllegalArgumentException("MtGox only supports the MtGoxExchangeServiceConfiguration");
  }
}
