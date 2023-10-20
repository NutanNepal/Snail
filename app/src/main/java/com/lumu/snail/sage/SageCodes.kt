package com.lumu.snail.sage

val SageCodes = mutableMapOf<String, String>(
    "Blank Sage" to "",
    "Primary Decomposition" to "R.<w,x,y,z>=PolynomialRing(QQ)\n" +
            "I=R.ideal(x*y,y*z,w*x,w*z)\n" +
            "I.primary_decomposition()",

    "Primary Decomposition (n-variate)" to "R=PolynomialRing(QQ,'x',4)\n" +
            "R.inject_variables()\n" +
            "I=R.ideal(x0*x1,x1*x3,x0*x2,x2*x3)\n" +
            "I.primary_decomposition()",

    "Multivariate Division" to
        "S.<x,y> = PolynomialRing(CC,2,'xy',order='degrevlex')\n" +
                "def multidiv(f, listf):\n" +
                "    listofqs = []\n" +
                "    p=f\n" +
                "    for fs in listf:\n" +
                "        q, r = p.quo_rem(fs)\n" +
                "        listofqs.append(q)\n" +
                "        p=r\n" +
                "    return (listofqs, p)\n\n" +
                "f=x^8+y^8\n" +
                "g=x*y^3-y^4\n"+
                "h=x^2*y^2-x*y^2\n"+
                "multidiv(f,(g,h))\n",

    "Buchberger's Algorithm" to
            "from sage.rings.polynomial.toy_buchberger import buchberger\n" +
            "\n" +
            "R.<w,x,y,z>=PolynomialRing(QQ)\n" +
            "I=R.ideal(x*y,y*z,w*x,w*z)\n" +
            "buchberger(I)"
)