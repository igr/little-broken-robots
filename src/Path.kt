
class Path(size : Int, directions : Array<Direction?>) {
	val size = size
	val dim = size * 2 + 3
	val matrix = Array(dim){ arrayOfNulls<Int>(dim) }
	var x = 0
	var y = 0
	var step = 1
	val path = directions;

	init {
		x = size + 1;
		y = size + 1;
		play(directions)
	}

	fun up() : Path? {
		val newy = y - 1;
		if (newy < 0) {
			return null
		}
		if (matrix[x][newy] != null) {
			return null
		}
		return Path(size + 1, path + Direction.UP)
	}
	fun left() : Path? {
		val newx = x - 1;
		if (newx < 0) {
			return null
		}
		if (matrix[newx][y] != null) {
			return null
		}
		return Path(size + 1, path + Direction.LEFT)
	}
	fun right() : Path? {
		val newx = x + 1;
		if (newx >= dim) {
			return null
		}
		if (matrix[newx][y] != null) {
			return null
		}
		return Path(size + 1, path + Direction.RIGHT)
	}
	fun down() : Path? {
		val newy = y + 1;
		if (newy >= dim) {
			return null
		}
		if (matrix[x][newy] != null) {
			return null
		}
		return Path(size + 1, path + Direction.DOWN)
	}


	fun play(path : Array<Direction?>) {
		var step = 1
		matrix[x][y] = step;

		for (dir in path) {
			when (dir) {
				Direction.UP -> {
					y--;
				}
				Direction.DOWN -> {
					y++;
				}
				Direction.LEFT -> {
					x--;
				}
				Direction.RIGHT -> {
					x++;
				}
			}
			step++;

			matrix[x][y] = step;
		}
	}

	fun print() {
		println("-------------------")
		for (x in matrix) {
			for (y in x) {
				if (y == null) print(".") else print(y)
			}
			println()
		}
		println("-------------------")
	}

}