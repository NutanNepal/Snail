package com.lumu.snail.generalWebView

val JavaScriptCodes = mutableMapOf<String, String>(
    "The Stacks Project" to """
                        var elementsToHide = ["first-bar", "second-bar"];
                        for (var i = 0; i < elementsToHide.length; i++) {
                            var element = document.getElementById(elementsToHide[i]);
                            if (element) {
                                element.style.display = 'none';
                            }
                        }
                """,

    "Kerodon.net" to """
                        var elementsToHide = ["first-bar", "second-bar", "move-it",
                        ];
                        for (var i = 0; i < elementsToHide.length; i++) {
                            var element = document.getElementById(elementsToHide[i]);
                            if (element) {
                                element.style.display = 'none';
                            }
                        }
                        // Add this code to automatically click the anchor
                        // link when the page is loaded
                        var anchorLink = document.querySelector('a[href="#selector"]');
                        if (anchorLink) {
                            anchorLink.click();
                        }
                        """,

    "githubCheck" to "",

    "AMSArticles" to """
            /*
                        var classesToHide = ["navbar yamm navbar-default navbar-fixed-top",
                        "col-md-12 col-sm-12 text-center", "img-responsive",
                        "col-md-6 col-md-offset-3", "shrink",
                        "col-md-12 col-sm-12",
                        "col-md-2 shrink"];

                        for (var i = 0; i < classesToHide.length; i++) {
                            var elements = document.getElementsByClassName(classesToHide[i]);
                            for (var j = 0; j < elements.length; j++) {
                                elements[j].style.display = 'none';
                            }
                        }
                        
                        var elementsToHide = ["footer", "lnavrail"
                        ];
                        for (var i = 0; i < elementsToHide.length; i++) {
                            var element = document.getElementById(elementsToHide[i]);
                            if (element) {
                                element.style.display = 'none';
                            }
                        }
            */
                        
                        var articles = document.getElementsByTagName("main");

                        if (articles.length > 0) {
                            var main = articles[0];
                            // Assuming you want to select the first <article> element
                            
                            // Reset margins and padding for the body and main
                            document.body.style.margin = "15px";
                            document.body.style.padding = "0";
                            main.style.margin = "0";
                            main.style.padding = "10sp";
    
                            document.body.innerHTML = main.innerHTML;
                        }
                        """
)