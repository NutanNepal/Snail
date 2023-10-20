package com.lumu.snail.tableOfContents

import java.util.Locale

enum class Category(
    var chapterList: List<String>
){
    Subjects(listOf(
        "Algebra", "Analysis", "Smooth Manifolds", "Algebraic Topology"
    )),
    Topics(listOf(
        "Basic Group Theory", "Field Theory", "Group Actions", "Hilbert Spaces",
        "Lp-Spaces", "Measure Theory",
        "Modules and Vector Spaces",
        "Metric Spaces", "Normed Spaces",
        "Representation Theory", "Ring Theory",
        "Singular Homology", "The Fundamental Group", "Vector Bundles",
        "Sylow Theorems", "ED, PID and UFD"
    )),
    Exercises(listOf(
    )),
    Notes(listOf(
        "Matroids", "Schemes",
    )),
    Sage(listOf(
        "Blank Sage", "Primary Decomposition", "Multivariate Division",
        "(Incomplete) Glossary", "Primary Decomposition (n-variate)",
        "Buchberger's Algorithm"
    )),
    OnlineResources(listOf(
        "The Stacks Project", "Kerodon.net"
    )),
    AMSArticles(listOf(
        "Definability and Arithmetic", "On the Geometry of Metric Spaces",
        "Recent Developments in Ricci Flows",
        "Moduli Spaces of Curves: Classical and Tropical",
        "Chip Firing and Algebraic Curves",
        "The Symmetric Group Through a Dual Perspective",
        "Could infty-Category Theory Be Taught to Undergraduates?",
        "Tropical Combinatorics",
        "Euclidean Traveller in Hyperbolic Worlds",
        "Contact Geometry and the Mapping Class Group",
        "An Invitation to Categorification"


    ))

}

enum class Subjects(
    var topics:List<String>
){
    Algebra(
        listOf(
            "Group Actions", "Basic Group Theory", "Field Theory",
            "Modules and Vector Spaces",
            "Representation Theory", "Ring Theory",
            "Sylow Theorems"
        )
    ),

    Analysis(
        listOf(
            "Metric Spaces", "Normed Spaces",
            "Hilbert Spaces", "Measure Theory"
        )
    ),

    SmoothManifolds(
        listOf(
            "Topology", "Vector Bundles",
            "Differential Forms"
        )
    ),

    AlgebraicTopology(
        listOf(
            "The Fundamental Group", "Singular Homology"
        )
    );

    companion object {
        fun isSubjectName(name: String): Boolean {
            return try {
                Subjects.valueOf(name)
                true
            } catch (e: IllegalArgumentException) {
                false
            }
        }

        fun addNewSubject(subjectName: String, chapterlist: List<String>) {
            // Create a new enum constant with the provided chapter list
            valueOf(subjectName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() })
                // Use capitalize to ensure enum name starts with an uppercase letter
                .topics = chapterlist
        }
    }
}