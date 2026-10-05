package com.example.secureFort.contr;


import org.springframework.http.MediaType;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfDemoController {

    @GetMapping(
        value = "/csrf-demo",
        produces = MediaType.TEXT_HTML_VALUE
    )
    public String showPurchaseForm(CsrfToken csrfToken) {

        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>SecureFort CSRF Demo</title>
            </head>
            <body>
                <h1>SecureFort Marketplace</h1>

                <p>Confirm a demo purchase.</p>

                <form action="/csrf-demo/purchase" method="post">
                    <input type="hidden"
                           name="%s"
                           value="%s"/>

                    <label>Product:</label>
                    <input type="text"
                           name="product"
                           value="Java Security Book"
                           readonly/>

                    <br/><br/>

                    <button type="submit">
                        Confirm Purchase
                    </button>
                </form>
            </body>
            </html>
            """.formatted(
                csrfToken.getParameterName(),
                csrfToken.getToken()
            );
    }

    @PostMapping(
        value = "/csrf-demo/purchase",
        produces = MediaType.TEXT_HTML_VALUE
    )
    public String confirmPurchase() {

        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Purchase Confirmed</title>
            </head>
            <body>
                <h1>Purchase Confirmed</h1>
                <p>
                    SecureFort accepted the request
                    after security validation.
                </p>
                <a href="/csrf-demo">Back to demo</a>
            </body>
            </html>
            """;
    }
}
