package com.lumu.snail.sage

class SageParse {

    companion object{

        fun generateHtmlContent(inputSage: String): String {
            return """
                <html>
                <head>
                    <style>
                        body {
                            background-color:#1C1B00;
                            color:#ffffff;
                            font-size: 25px;
                            line-height: 1.3;
                        }
                    </style>
                    <script src="https://sagecell.sagemath.org/static/embedded_sagecell.js"></script>
                    <script>
                        sagecell.makeSagecell({
                            inputLocation:'.sage',
                            autoeval:1,
                            hide:['editor', 'evalButton', 'permalink']
                        });
                    </script>
                </head>
                <body>
                    <div class = "sage">
                        <script type="text/x-sage" id="sage">$inputSage</script>
                    </div>
                </body>
                </html>
            """.trimIndent()
        }
    }
}