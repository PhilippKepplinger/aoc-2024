package at.pkepp.puzzle21

/**
 * Finds all shortest paths from one char to another char on a given pad
 */
class PadDecoder(private val pad: Pad) {

    private val cache = HashMap<String, List<String>>()

    fun getShortestPaths(from: Char,
                         to: Char): List<String> {

        val key = "${from}${to}"
        if (cache.contains(key)) {
            return cache.getValue(key)
        }

        val allPaths = getAllPaths(from, to)
        val shortestPaths = allPaths.filter { it.length == allPaths[0].length }
        cache[key] = shortestPaths
        return shortestPaths
    }

    private fun getAllPaths(from: Char,
                            to: Char): List<String> {
        return getPaths(
            pad.getButton(from),
            pad.getButton(to),
            "",
            mutableListOf()
        ).sortedBy {
            it.length
        }
    }

    private fun getPaths(current: Button?,
                         to: Button,
                         currentPath: String,
                         visited: MutableList<Button>): List<String> {
        if (current == null) {
            return listOf()
        }

        if (current == to) {
            return listOf(currentPath + "A")
        }

        val paths = mutableListOf<String>()
        visited.add(current)

        if (current.left != null && !visited.contains(current.left)) {
            paths.addAll(
                getPaths(
                    current.left,
                    to,
                    "$currentPath<",
                    visited.toMutableList()
                )
            )
        }
        if (current.right != null && !visited.contains(current.right)) {
            paths.addAll(
                getPaths(
                    current.right,
                    to,
                    "$currentPath>",
                    visited.toMutableList()
                )
            )
        }
        if (current.up != null && !visited.contains(current.up)) {
            paths.addAll(
                getPaths(
                    current.up,
                    to,
                    "$currentPath^",
                    visited.toMutableList()
                )
            )
        }
        if (current.down != null && !visited.contains(current.down)) {
            paths.addAll(
                getPaths(
                    current.down,
                    to,
                    "${currentPath}v",
                    visited.toMutableList()
                )
            )
        }

        return paths
    }

}