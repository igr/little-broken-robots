class StartPoint(x : Int, y : Int, size : Int, paths: List<Path>) {

	val x = x
	val y = y
	val size = size
	var paths = paths;

	var currentPathIndex = 0

	fun hasNext() : Boolean {
		return currentPathIndex < paths.size - 1;
	}

	/**
	 * Returns current path.
	 */
	fun path() : Path {
		return paths[currentPathIndex]
	}

	fun next() {
		currentPathIndex++;
	}

	fun reset() {
		currentPathIndex = 0;
	}

	fun removePath() {
		paths = paths.subList(0, currentPathIndex) + paths.subList(currentPathIndex + 1, paths.size)
		currentPathIndex--
	}

}
