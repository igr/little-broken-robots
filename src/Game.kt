import java.math.BigInteger

fun main(args: Array<String>) {

	val board = Board()

	val max = board.max

	println("Max: ${max}")

	// prepare array of all possible movements for each number

	val allMovements = calcAllMovements(board, max)

	// prepare game looping data

	var sp = board.calcStarPoints(allMovements)

	println("Starts: ${sp.size}")

	printTotalCounts(sp)

	// reduce start points paths

	reduceStartingPoints(board, sp)

	// sort start points

	sp = sp.sortedBy { it.size  }.reversed()

	printTotalCounts(sp)

	// main loop

	//solve1(board, sp)
	solve2(board, sp)
}

private fun reduceStartingPoints(board: Board, sp: List<StartPoint>) {
	board.resetBoard()
	var ndx = 0
	for (p in sp) {
		ndx++
		val size = p.paths.size

		while (true) {
			if (!board.testPath(p)) {
				p.removePath()
			}

			if (p.hasNext()) {
				p.next()
			} else {
				p.reset()
				break
			}
		}

		println("Point ${ndx}: ${size} => ${p.paths.size}")
	}
}

private fun printTotalCounts(sp: List<StartPoint>) {
	var total = BigInteger("1");
	sp.forEach {
		print("${it.paths.size} * ")
		total = total.multiply(BigInteger(it.paths.size.toString()))
	}

	println(" = ${total}")
}

private fun solve2(board: Board, sp: List<StartPoint>) {
	var count = 0;

	while (true) {
		board.resetBoard()

		var ndx = 0

	bigloop@
		while (ndx < sp.size) {
			val point = sp[ndx]

			count++

			//print("\t->${ndx}\t")
			if (board.draw(point) == false) {
				// NEXT!
				if (point.hasNext()) {
					point.next()
					break		// breaks main loop and start again
					// this is a place for optimization, as we can simply rollback
					// current drawing and just continue; instead of drawing it all again!
				}
				//
				var neeext = false
				point.reset()
				for (ndx2 in 0 .. (ndx - 1)) {
					val newNdx = ndx - ndx2 - 1
					val p = sp[newNdx]
					if (p.hasNext()) {
						p.next()	// breaks this small loop as we have found the next position
						neeext = true
						//ndx = newNdx
						//break
						ndx = 0
						break@bigloop
					} else {
						p.reset()
					}
				}

				if (!neeext) {
					println("NOT FOUND in ${count}")
					return
				}
			}
			ndx++;
		}
		if (ndx == sp.size) {
			break
		}
	}

	println("Solution found:!")
	printSolution(sp);

	println("Total iterations: ${count}")
}


private fun solve1(board: Board, sp: List<StartPoint>) {
	var running = true
	var count = 0;
	while (running) {
		// try out current combination
		count++
		val found = board.test(sp)

		if (found) {
			printSolution(sp)
			break
		}

		// next
		running = false
		for (point in sp) {
			if (point.hasNext()) {
				point.next()
				running = true
				break;
			} else {
				point.reset()
			}
		}
	}

	println("Total iterations: ${count}")
}

private fun printSolution(sp: List<StartPoint>) {
	println("----------------")
	println("Found solution!")

	var i = 1
	for (s in sp) {
		println("Point ${i}: (${s.x+1},${s.y+1}):${s.size}")
		for (d in s.path().path) {
			print(d)
			print(' ')
		}
		println()
		println()
		i++
	}

	println("----------------")
	return
}

private fun calcAllMovements(board: Board, max: Int): Array<List<Path>> {
	val zero = Path(0, arrayOfNulls(0))

	val allMovements = Array(max + 1) { emptyList<Path>() }
	allMovements[0] = listOf(zero)

	println("Calculating all possible movements:")
	for (i in 1..board.max) {
		print("start ${i} - ")

		val prevPaths = allMovements[i - 1]
		val movements = arrayListOf<Path>()

		for (p in prevPaths) {
			val up = p.up()
			if (up != null) {
				movements.add(up)
				//up.print()
			}
			val down = p.down()
			if (down != null) {
				movements.add(down)
				//down.print()
			}
			val left = p.left()
			if (left != null) {
				movements.add(left)
				//left.print()
			}
			val right = p.right()
			if (right != null) {
				movements.add(right)
				//right.print()
			}
		}

		allMovements[i] = movements;
		println("total ${movements.size}")
	}
	return allMovements
}
