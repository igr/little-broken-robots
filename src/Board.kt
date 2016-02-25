class Board {

	//val data = "2..XX1.3...4....5.....6......|"

	val data = board

	val max = findMax()
	var width = 0
	var height = 0

	private fun findMax() : Int {
		var m = 0
		for (c in data) {
			if (c == '|') {
				height++
				continue
			} else {
				if (height == 0) {
					width++
				}
			}
			if (isStart(c)) {
				val n = startValue(c)
				if (n > m) {
					m = n
				}
			}
		}
		return m
	}

	fun calcStarPoints(allMovements: Array<List<Path>>): List<StartPoint> {
		val startpoints : MutableList<StartPoint> = arrayListOf()
		var x = 0
		var y = 0

		for (c in data) {
			if (c == '|') {
				y++
				x = 0
				continue
			}

			if (isStart(c)) {
				val v = startValue(c)
				val movements : List<Path> = allMovements[v]
				val sp = StartPoint(x, y, v, movements.toCollection(arrayListOf<Path>()))

				startpoints.add(sp)
			}
			x++
		}

		return startpoints
	}

	private fun isStart(c : Char): Boolean {
		return c > '0' && c <= '9'
	}
	private fun isWall(c : Char): Boolean {
		return c == 'X'
	}

	private fun startValue(c : Char): Int {
		return c - '0'
	}

	var matrix = Array(width){ arrayOfNulls<Int>(height) }

	/**
	 * Resets board.
	 */
	public fun resetBoard() {
		var x = 0
		var y = 0

		for (c in data) {
			if (c == '|') {
				y++
				x = 0
				continue
			}

			if (isStart(c)) {
				matrix[x][y] = startValue(c)
			}
			else if (isWall(c)) {
				matrix[x][y] = -1
			}
			else {
				matrix[x][y] = 0
			}
			x++
		}
	}

	fun test(startPoints: List<StartPoint>): Boolean {
		resetBoard()

		for (sp in startPoints) {
			val path = sp.path()
			var spx = sp.x
			var spy = sp.y
			val mark = sp.size

			matrix[spx][spy] = mark

			for (d in path.path) {
				when (d) {
					Direction.UP -> {
						spy--
					}
					Direction.DOWN -> {
						spy++
					}
					Direction.LEFT -> {
						spx--
					}
					Direction.RIGHT -> {
						spx++
					}
				}

				if (spx < 0) {
					return false
				}
				if (spy < 0) {
					return false
				}
				if (spx >= width) {
					return false
				}
				if (spy >= height) {
					return false
				}

				if (matrix[spx][spy] != 0) {
					return false
				}
				matrix[spx][spy] = mark
			}
		}

		return true
	}

	fun testPath(sp : StartPoint): Boolean {
		val path = sp.path()
		var spx = sp.x
		var spy = sp.y

		for (d in path.path) {
			when (d) {
				Direction.UP -> {
					spy--
				}
				Direction.DOWN -> {
					spy++
				}
				Direction.LEFT -> {
					spx--
				}
				Direction.RIGHT -> {
					spx++
				}
			}

			if (spx < 0) {
				return false
			}
			if (spy < 0) {
				return false
			}
			if (spx >= width) {
				return false
			}
			if (spy >= height) {
				return false
			}

			if (matrix[spx][spy] != 0) {
				return false
			}
		}

		return true
	}

	/**
	 * Draws current path.
	 */
	fun draw(sp :StartPoint) : Boolean {
		var spx = sp.x
		var spy = sp.y

		//println("\tdrawing: ${sp.size} ${sp.currentPathIndex}")

		for (d in sp.path().path) {
			when (d) {
				Direction.UP -> {
					spy--
				}
				Direction.DOWN -> {
					spy++
				}
				Direction.LEFT -> {
					spx--
				}
				Direction.RIGHT -> {
					spx++
				}
			}

			if (spx < 0) {
				return false
			}
			if (spy < 0) {
				return false
			}
			if (spx >= width) {
				return false
			}
			if (spy >= height) {
				return false
			}

			if (matrix[spx][spy] != 0) {
				return false
			}

			matrix[spx][spy] = sp.size
		}

		return true

	}

}
