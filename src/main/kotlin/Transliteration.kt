import java.lang.StringBuilder

/**
 * @author zp4rker
 */
object Transliteration {

    fun convert(input: String): String {
        val harakaat = arrayOf('a', 'i', 'u', 'ā', 'ī', 'ū')

        val sb = StringBuilder()

        val chars = input.trim().toCharArray()
        var skips = 0


        for (i in chars.indices) {
            if (skips > 0) {
                skips--
                continue
            }

            if (chars.getOrNull(i + 1) == 'l' && chars.getOrNull(i + 2) == 'l' && chars.getOrNull(i + 3) == 'ā' && chars.getOrNull(i + 4) == 'h') {
                sb.append("\u0627\u0644\u0644\u0651\u0670")
                skips += 3
                continue
            }

            val out = when (chars[i]) {
                // ḥurūf
                'b' -> "\u0628"

                't' -> when (chars.getOrNull(i + 1)) {
                    'h' -> {
                        skips++
                        "\u062B"
                    }
                    ',' -> {
                        skips++
                        "\u0629"
                    }
                    else -> "\u062A"
                }

                'j' -> "\u062C"

                'ḥ' -> "\u062D"

                'k' -> {
                    if (chars.getOrNull(i + 1) == 'h') {
                        skips++
                        "\u062E"
                    } else {
                        "\u0643"
                    }
                }

                'd' -> {
                    if (chars.getOrNull(i + 1) == 'h') {
                        skips++
                        "\u0630"
                    } else {
                        "\u062F"
                    }
                }

                'r' -> "\u0631"

                'z' -> "\u0632"

                's' -> {
                    if (chars.getOrNull(i + 1) == 'h') {
                        skips++
                        "\u0634"
                    } else {
                        "\u0633"
                    }
                }

                'ṣ' -> "\u0635"

                'ḍ' -> "\u0636"

                'ṭ' -> "\u0637"

                'ẓ' -> "\u0638"

                '\'' -> "\u0639"

                'g' -> {
                    skips++
                    "\u063A"
                }

                'f' -> "\u0641"

                'q' -> "\u0642"

                'l' -> "\u0644"

                'm' -> "\u0645"

                'n' -> "\u0646"

                'h' -> if (chars.getOrNull(i + 1) == ',') "\u0629" else "\u0647"

                'w' -> "\u0648"

                'y' -> "\u064A"

                '-' -> {
                    if (chars.getOrNull(i + 1) == 'ā') {
                        skips++
                        "\u0622"
                    } else if (harakaat.any { it == chars.getOrNull(i + 1) }) {
                        "\u0621"
                    } else if (harakaat.any { it == chars.getOrNull(i - 1) } && harakaat.none { it == chars.getOrNull(i + 1) }) {
                        "\u0621\u0652"
                    } else {
                        ""
                    }
                }

                // definite particle

                'a', 'i', 'u' -> {
                    if (arrayOf(' ', null).any { it == chars.getOrNull(i - 1) }) {
                        "\u0627"
                    } else if (chars.getOrNull(i - 1) == '-') {
                        "\u0621"
                    } else {
                        ""
                    }
                }

                // elongation

                'ā' -> if (arrayOf(' ', null).any { it == chars.getOrNull(i - 1) }) "\u0622" else "\u0627"

                'ī' -> if (chars.getOrNull(i + 1) != null) "\u064A" else "\u0649"

                'ū' -> "\u0648"

                // space

                ' ' -> " "

                else -> ""
            }

            sb.append(out)

            if (harakaat.none { it == chars[i] } && chars[i] == chars.getOrNull(i + 1)) { // shaddah
                sb.append("\u0651")
            }
        }

        return sb.toString().trim()
    }

}