package com.example.calculator

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator20.R
import java.sql.BatchUpdateException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    //    结果集
    private var editText: EditText? = null

    //数字1-9
    private var main_btn1: Button? = null
    private var main_btn2: Button? = null
    private var main_btn3: Button? = null
    private var main_btn4: Button? = null
    private var main_btn5: Button? = null
    private var main_btn6: Button? = null
    private var main_btn7: Button? = null
    private var main_btn8: Button? = null
    private var main_btn9: Button? = null
    private var main_btn0: Button? = null

    //运算符
    private var main_btn1a // +
            : Button? = null
    private var main_btnj // -
            : Button? = null
    private var main_btnx // *
            : Button? = null
    private var main_btnc // /
            : Button? = null
    private var main_btnd //小数点
            : Button? = null
    private var main_btnMod // %
            : Button? = null
    private var main_btn1d //=
            : Button? = null

    //括号
    private var main_btn_left_bracket //左括号（
            : Button? =null
    private var main_btn_right_bracket //右括号）
            : Button? =null
    //sin,cos,tan
    private var main_btn_sin:Button?=null
    private var main_btn_cos:Button?=null
    private var main_btn_tan:Button?=null
    //清除
    private var main_btndel: Button? = null
    private var main_btnAC: Button? = null
    var clear_flag //清空标识
            = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var configuration : Configuration= this.getResources().getConfiguration();
        var ori : Int = configuration.orientation;
        //横屏
        if(ori == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.acticity_horition)
            //横屏的特殊按钮
            main_btnMod = findViewById<View>(R.id.button11) as Button
            main_btn_right_bracket = findViewById<View>(R.id.button48) as Button
            main_btn_left_bracket = findViewById<View>(R.id.button47) as Button
            main_btn_sin = findViewById<View>(R.id.button46) as Button
            main_btn_cos = findViewById<View>(R.id.button45) as Button
            main_btn_tan = findViewById<View>(R.id.button44) as Button
        }
        //竖屏c
        else if(ori == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_dark)
        }

        //数字1-9
        main_btn1 = findViewById<View>(R.id.button18) as Button
        main_btn2 = findViewById<View>(R.id.button19) as Button
        main_btn3 = findViewById<View>(R.id.button20) as Button
        main_btn4 = findViewById<View>(R.id.button22) as Button
        main_btn5 = findViewById<View>(R.id.button23) as Button
        main_btn6 = findViewById<View>(R.id.button24) as Button
        main_btn7 = findViewById<View>(R.id.button26) as Button
        main_btn8 = findViewById<View>(R.id.button27) as Button
        main_btn9 = findViewById<View>(R.id.button28) as Button
        main_btn0 = findViewById<View>(R.id.button31) as Button
//        //运算符
        main_btn1a = findViewById<View>(R.id.button32) as Button // +
        main_btnj = findViewById<View>(R.id.button35) as Button // -
        main_btnx = findViewById<View>(R.id.button29) as Button // *
        main_btnc = findViewById<View>(R.id.button33) as Button // /
        main_btnd = findViewById<View>(R.id.button30) as Button //小数点
        main_btn1d = findViewById<View>(R.id.button34) as Button //=
        main_btndel = findViewById<View>(R.id.button25) as Button //回退
        main_btnAC = findViewById<View>(R.id.button21) as Button //清空
        editText = findViewById<View>(R.id.main_et_result) as EditText //结果集
    }

    //简易计算器：文本
    fun onClick(view: View) {
        //获取文本内容
        var input = editText!!.text.toString()
        when (view.id) {
            R.id.button30, R.id.button18, R.id.button19, R.id.button20, R.id.button22, R.id.button23, R.id.button24, R.id.button26, R.id.button27, R.id.button28, R.id.button31 -> {
                if (clear_flag) {
                    clear_flag = false
                    editText!!.setText("") //赋值为空
                    input=""
                }
                editText!!.setText(input + (view as Button).text) //结果集就为本身
                editText!!.setSelection(input.length+1)
            }
            R.id.button11, R.id.button32, R.id.button35, R.id.button29, R.id.button33 -> {

                if (input.equals("")) {
                    editText!!.setText(" " + (view as Button).text.toString() + " ")
                }
                //判断是否前一个字符为运算符，true的话就覆盖，false的话直接添加
                else if (input.substring(input.length - 1, input.length) == " ") {
                    editText!!.setText(input.substring(0, input.indexOf(" ")) + " " + (view as Button).text.toString() + " ")
                    editText!!.setSelection(input.length + 1)
                } else {
                    editText!!.setText(input + " " + (view as Button).text + " ")
                    //判断前面的数字满足运算条件，满足的话立即计算结果
                    val exp = editText!!.text.toString() //获取文本框的内容
                    //运算符前的数字
                    val s1 = exp.substring(0, exp.indexOf(" "))
                    //运算符后的数字
                    val s2 = exp.substring(exp.indexOf(" ") + 3)
                    if (s1 != "" && s2 != "") {
                        editText!!.setText(exp.substring(0, exp.length - 3))
                        result
                        editText!!.setText(editText!!.text.toString() + " " + view.text + " ")
                        clear_flag = false
                    } else {

                    }
                }
            }

            R.id.button25 -> if (clear_flag) {
                clear_flag = false
                input = ""
                editText!!.setText("")
            } else if (input != null && input != "") { //如果获取到的内容为空
                editText!!.setText(input.substring(0, input.length - 1)) //结果集为空
                editText!!.setSelection(input.length-1)
            }
            R.id.button21 -> editText!!.setText("")
            R.id.button34 -> result //调用处理结果集的方法
        }
    }//如果是只输入运算符后面的数

    //逆波兰计算器:文本
    fun onClick2(view: View) {
        //获取文本内容
        var input = editText!!.text.toString()
        when (view.id) {
            R.id.button30, R.id.button18, R.id.button19, R.id.button20, R.id.button22, R.id.button23, R.id.button24, R.id.button26, R.id.button27, R.id.button28, R.id.button31 -> {
                if (clear_flag) {
                    clear_flag = false
                    editText!!.setText("") //赋值为空
                    input = ""
                }
                editText!!.setText(input + (view as Button).text) //结果集就为本身
                editText!!.setSelection(input.length + 1)
            }
            R.id.button47, R.id.button48 -> {
                if (clear_flag) {
                    clear_flag = false
                    editText!!.setText("") //赋值为空
                }
                editText!!.setText(input + " " + (view as Button).text.toString() + " ") //结果集就为本身
                editText!!.setSelection(input.length + 3)
            }
            R.id.button46, R.id.button45, R.id.button44 -> {
                if (clear_flag) {
                    clear_flag = false
                    editText!!.setText("") //赋值为空
                }
                editText!!.setText((view as Button).text.toString()) //结果集就为本身

            }
            R.id.button11, R.id.button32, R.id.button35, R.id.button29, R.id.button33 ->{
                clear_flag = false
            if (input.equals("")) {
                editText!!.setText(" " + (view as Button).text.toString() + " ")
            }
            //判断是否前一个字符为运算符，true的话就覆盖，false的话直接添加
            else if (input.substring(input.length - 1, input.length) == " ") {
                if (input.substring(input.length - 2, input.length - 1) != ")" && input.substring(input.length - 2, input.length - 1) != "(") {
                    editText!!.setText(input.substring(0, input.indexOf(" ")) + " " + (view as Button).text.toString() + " ")
                } else {
                    editText!!.setText(input + " " + (view as Button).text + " ")
                }

            } else {
                editText!!.setText(input + " " + (view as Button).text + " ")
            }
        }
            R.id.button25 -> if (clear_flag) {
                clear_flag = false
                editText!!.setText("")
            } else if (input != null && input != "") { //如果获取到的内容为空
                editText!!.setText(input.substring(0, input.length - 1)) //结果集为空
                editText!!.setSelection(input.length-1)
            }
            R.id.button21 -> editText!!.setText("")
            R.id.button34 -> result2 //调用处理结果集的方法
        }
    }


    //简易计算器：求值
    private val result: Unit
        private get() {
            val exp = editText!!.text.toString() //获取文本框的内容
            if (exp == null || exp == "") {
                return
            }
            if (!exp.contains(" ")) {
                return
            }
            if (clear_flag) {
                clear_flag = false
                return
            }
            clear_flag = true
            var result = 0.0

            //进行截取
            //运算符前的数字
            val s1 = exp.substring(0, exp.indexOf(" "))
            //运算符
            val op = exp.substring(exp.indexOf(" ") + 1, exp.indexOf(" ") + 2)
            //运算符后的数字
            val s2 = exp.substring(exp.indexOf(" ") + 3)
            if (s1 != "" && s2 != "") { //如果包含小数点的运算
                val d1 = s1.toDouble() //则数字都是double类型
                val d2 = s2.toDouble()
                if (op == "+") { //如果是 +
                    result = d1 + d2
                } else if (op == "-") {
                    result = d1 - d2
                } else if (op == "*") {
                    result = d1 * d2
                } else if (op == "/") {
                    result = if (d2 == 0.0) { //如果被除数是0
                        0.0 //则结果是0
                    } else { //否则执行正常是除法运算
                        d1 / d2
                    }
                }else if(op == "%"){
                    result = d1 % d2;
                }
                if (!s1.contains(".") && !s2.contains(".") && op != "/") { //如果是整数类型
                    val r = result.toInt() //都是整形
                    editText!!.setText(r.toString() + "")
                } else {
                    editText!!.setText(result.toString() + "")
                }
            } else if (s1 != "" && s2 == "") { //如果是只输入运算符前的数
                editText!!.setText(s1) //直接返回当前文本框的内容 (去掉运算符即可)
            } else if (s1 == "" && s2 != "") { //如果是只输入运算符后面的数
                val d2 = s2.toDouble()

                //运算符前没有输入数字
                if (op == "+") {
                    result = 0 + d2
                } else if (op == "-") {
                    result = 0 - d2
                } else  {
                    result = 0.0
                }
                if (!s1.contains(".") && !s2.contains(".")) {
                    val r = result.toInt()
                    editText!!.setText(r.toString() + "")
                } else {
                    editText!!.setText(result.toString() + "")
                }
            } else {
                editText!!.setText("")
            }
        }

    //逆波兰计算器：求值
    private val result2: Unit
        private get() {
            val exp = editText!!.text.toString() //获取文本框的内容
            if (exp == null || exp == "") {
                return
            }
            if(exp.length > 4 ){
                var res = 0.0
                var num = exp.substring(4)
                if(exp.substring(0,4).equals("sin(")){
                    res = Math.sin(num.toDouble())
                    editText!!.setText(res.toString())
                    return
                }else if(exp.substring(0,4).equals("cos(")){
                    res = Math.cos(num.toDouble())
                    editText!!.setText(res.toString())
                    return
                }else if(exp.substring(0,4).equals("tan(")){
                    res = Math.tan(num.toDouble())
                    editText!!.setText(res.toString())
                    return
                }

            }
            if (!exp.contains(" ")) {
                return
            }
            if (clear_flag) {
                clear_flag = false
                return
            }
            clear_flag = true



            // 写一个方法将中缀表达式转成对应的List  get:MutableList<String>
            val infixExpressionList = toInfixExpressionList(exp)

            //得到逆波兰数组
            val suffixExpressionList = parseSuffixExpressionList(infixExpressionList)

            //计算
            val res = calculate(suffixExpressionList)

            if(res-res.toInt()< 10E-6){
                editText!!.setText(res.toInt().toString())
            }
            else {
                editText!!.setText(res.toDouble().toString())
            }

        }



    fun calculate(ls: List<String>): Double {
        // 创建栈
        val stack: Stack<String> = Stack<String>()
        val nonNegaIntRegex = Regex("\\d+")
        val IntRegex =Regex("(-?\\d+)(\\.\\d+)?")
        // 遍历ls
        for (item in ls) {
            // 使用正则表达式来取出数据

            if (nonNegaIntRegex.matches(item) || IntRegex.matches(item)) { // 匹配的是多位数
                // 入栈
                stack.push(item)
            } else {
                // pop出两个数,并运算,在入栈
                val num2: Double = stack.pop().toDouble()
                val num1: Double = stack.pop().toDouble()
                var res = 0.0
                res = if (item == "+") {
                    num1 + num2
                } else if (item == "-") {
                    num1 - num2
                } else if (item == "*") {
                    num1 * num2
                } else if (item == "/") {
                    num1 / num2
                } else if (item == "%") {
                    num1 % num2
                }else {
                    throw RuntimeException("运算符有误")
                }
                // 将结果入栈
                stack.push("" + res)
            }
        }


        // 将栈中的最后一个结果返回
        return stack.pop().toDouble()
    }

    // 写一个方法将中缀表达式转成对应的List  get:MutableList<String>,要保证进来的中缀表达式不为空
    fun toInfixExpressionList(s: String): List<String> {
        // 定义一个List,存放中缀表达式对应的内容
        var ls : MutableList<String> = ArrayList()
        var flag = 0

            //一开始就是运算符的情况
            if(s.length>=3 && s[0].equals(" ")){
                ls = s.substring(3).split(" ") as MutableList<String>
                if(s[1].equals("-")){
                    ls[0] = "-"+ls[0]
                }

                else{
                    Log.d("calc_err", "算式开头格式不对")
                }

            }else{
                ls = s.split(" ") as MutableList<String>
            }

        while(ls.indexOf("")!=-1){
            ls.remove("")
        }
        return ls
    }

    //得到逆波兰数组
    fun parseSuffixExpressionList(ls: List<String>): List<String> {
        //定义两个栈
        val s1: Stack<String> = Stack<String>() //符号栈
        //由于s2在整个转换过程中,没有pop操作.而且我们还需要逆序输出,
        //因此我们使用List<String>代替
        val s2: MutableList<String> = ArrayList()
        val nonNegaIntRegex = Regex("\\d+")
        val IntRegex =Regex("(-?\\d+)(\\.\\d+)?")
        //遍历ls
        for (item in ls) {
            //如果是一个数,加入s2

            if (nonNegaIntRegex.matches(item) || IntRegex.matches(item)) {
                s2.add(item)
            } else if (item == "(") {
                s1.push(item)
            } else if (item == ")") {
                //如果是右括号")" 则依次弹出栈顶的运算符,并压入s2,直到遇到左括号为止,此时将这一对括号丢弃
                while (!s1.peek().equals("(")) {
                    s2.add(s1.pop())
                }
                s1.pop() //将对应的"("弹出s1栈,消除小括号
            } else {
                //当item的小于等于s1栈顶运算符,将s1栈顶的运算符弹出并加入到s2中,再次转到(4.1)与s1中新的栈顶运算符相比较
                //缺少一个比较优先级高低的方法
                while (s1.size !== 0 && getValue(s1.peek()[0]) >= getValue(item[0])) {
                    s2.add(s1.pop())
                }
                //还需要把item压入栈
                s1.push(item)
            }
        }
        //将s1中剩余的运算符依次弹出并加入s2
        while (s1.size !== 0) {
            s2.add(s1.pop())
        }
        return s2 //注意由于存放到List中,因此安顺序输出就是对应的后缀表达式
    }

    //运算符优先级
    fun getValue(operation: Char): Int {
        var result = 0
        when (operation) {
            '+' -> result = 1
            '-' -> result = 1
            '/' -> result = 2
            '*' -> result = 2
            '%' -> result = 2
            else -> result = -1
        }
        return result
    }
}
