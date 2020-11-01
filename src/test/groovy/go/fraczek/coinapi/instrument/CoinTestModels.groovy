package go.fraczek.coinapi.instrument


import org.json.JSONObject

class CoinTestModels {

    static getNotValidProductMessage(productName) {
        return new JSONObject('{' +
                '"type":"error",' +
                '"message":"Failed to subscribe",' +
                '"reason":"' + productName + ' is not a valid product"' +
                '}')
    }

    static getTickerProductMessage(productId) {
        return new JSONObject('{' +
                '"type":"ticker",' +
                '"sequence":20415470,' +
                '"product_id":"' + productId + '",' +
                '"price":"11165.21",' +
                '"open_24h":"8869.62",' +
                '"volume_24h":"1904.23617585",' +
                '"low_24h":"8869.62",' +
                '"high_24h":"11174.13",' +
                '"volume_30d":"49338.35531813",' +
                '"best_bid":"11165.21",' +
                '"best_ask":"11165.23",' +
                '"side":"sell",' +
                '"time":"2020-10-28T17:28:35.090946Z",' +
                '"trade_id":3426179,' +
                '"last_size":"0.00089563"}')
    }
}
