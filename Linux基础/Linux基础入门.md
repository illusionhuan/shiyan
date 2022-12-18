# 												Linux基础入门

## 1.重要快捷键

[tab]	使用tab键进行命令补全

[ctrl+c]	使用ctrl+c键强行终止当前程序

[ctrl+d]	键盘输入结束或退出终端

[ctrl+s] 	暂停当前程序，暂停后按下任意键恢复运行

[ctrl+k] 	删除从光标所在位置到行末

[alt+backspace]	向前删除一个单词 

## 2.使用方向上键可以恢复之前输入过的命令

## 3.使用通配符

查询符合要求的文件

```shell
ls *.txt
```

在创建文件的时候，如果需要一次性创建多个文件，比如：**“love_1_linux.txt，love_2_linux.txt，... love_10_linux.txt”**。在 Linux 中十分方便：

```shell
touch love_{1..10}_shiyanlou.txt
```



|          字符           |                    含义                    |
| :---------------------: | :----------------------------------------: |
|           `*`           |             匹配 0 或多个字符              |
|           `?`           |              匹配任意一个字符              |
|        `[list]`         |         匹配 list 中的任意单一字符         |
|        `[^list]`        |  匹配 除 list 中的任意单一字符以外的字符   |
|        `[c1-c2]`        | 匹配 c1-c2 中的任意单一字符 如：[0-9][a-z] |
| `{string1,string2,...}` | 匹配 string1 或 string2 (或更多)其一字符串 |
|       `{c1..c2}`        |      匹配 c1-c2 中全部字符 如{1..10}       |

## 4.用户及文件权限管理

`who` 命令其它常用参数

| 参数 |            说明            |
| :--: | :------------------------: |
| `-a` |      打印能打印的全部      |
| `-d` |       打印死掉的进程       |
| `-m` |   同`am i`，`mom likes`    |
| `-q` | 打印当前登录用户数及用户名 |
| `-u` |  打印当前登录用户登录信息  |
| `-r` |        打印运行等级        |

## 5.创建新用户

#### su，su- 与 sudo

**需要注意 Linux 环境下输入密码是不会显示的。**

`su <user>` 可以切换到用户 user，执行时需要输入目标用户的密码，`sudo <cmd>` 可以以特权级别运行 cmd 命令，需要当前用户属于 sudo 组，且需要输入当前用户的密码。`su - <user>` 命令也是切换用户，但是同时用户的环境变量和工作目录也会跟着改变成目标用户所对应的。

新建一个叫lilei的新用户

```shell
sudo adduser lilei
```

这个命令不但可以添加用户到系统，同时也会默认为新用户在 /home 目录下创建一个工作目录：

```shell
ls /home
```

使用创建的用户登录,使用以下命令切换登录用户

```shell
su -l lilei
```

退出当前用户跟退出终端一样，可以使用 `exit` 命令或者使用快捷键 `Ctrl+D`。

## 6.查看和添加用户组

### 方法一:使用groups命令

```shell
groups shiyanlou
```

### 方法二:查看/etc/groups文件

```shell
cat /etc/group | sort
```

这里 `cat` 命令用于读取指定文件的内容并打印到终端输出，后面会详细讲它的使用。 `| sort` 表示将读取的文本进行一个字典排序再输出，然后你将看到如下一堆输出，你可以在最下面看到 shiyanlou 的用户组信息

还可以使用grep命令过滤掉一些不想看到的结果

```shell
cat /etc/group | grep -E "shiyanlou"
```

/etc/group 的内容包括用户组（Group）、用户组口令、GID（组 ID） 及该用户组所包含的用户（User），每个用户组一条记录。格式如下：

> group_name:password:GID:user_list

你看到上面的 password 字段为一个 `x`，并不是说密码就是它，只是表示密码不可见而已。

这里需要注意，如果用户的 GID 等于用户组的 GID，那么最后一个字段 `user_list` 就是空的，这里的 GID 是指用户默认所在组的 GID，可以使用 `id` 命令查看。比如 shiyanlou 用户，在 `/etc/group` 中的 shiyanlou 用户组后面是不会显示的。lilei 用户，在 `/etc/group` 中的 lilei 用户组后面是不会显示的。

### 将其他用户添加到sudo用户组

shiyanlou 用户执行 sudo 命令将 lilei 添加到 sudo 用户组，让它也可以使用 sudo 命令获得 root 权限，首先我们切换回 shiyanlou 用户:

```shell
su - shiyanlou
```

进行以下操作:

```shell
groups lilei
sudo usermod -G sudo lilei
groups lilei
```

切换到lilei用户,使用sudo获取root权限

```shell
su - lilei
sudo ls /
```

### 删除用户和用户组

```shell
sudo deluser lilei --remove-home
```

使用 `--remove-home` 参数在删除用户时候会一并将该用户的工作目录一并删除。如果不使用那么系统会自动在 /home 目录为该用户保留工作目录。

删除用户组可以使用 `groupdel` 命令，倘若该群组中仍包括某些用户，则必须先删除这些用户后，才能删除群组。

## 7.查看文件权限

使用`ls`命令列出文件

```shell
ls -l
```

![image-20220722142342235](Typora\typora-user-images\image-20220722142342235.png)



![image-20220722142344204](Typora\typora-user-images\image-20220722142344204.png)

### 文件权限

读权限，表示你可以使用 `cat <file name>` 之类的命令来读取某个文件的内容；写权限，表示你可以编辑和修改某个文件的内容；

**一个目录同时具有读权限和执行权限才可以打开并查看内部文件，而一个目录要有写权限才允许在其中创建其它文件**，

文件大小

> 以 inode 结点大小为单位来表示的文件大小，你可以给 ls 加上 `-lh` 参数来更直观的查看文件的大小。

ls命令其他常用的用法:

- 显示除了.(当前目录)和..(上一级目录)之外的所有文件,包括隐藏文件(Linux下以.开头的文件为隐藏文件)

```shell
ls -a
```

当然也可以同时使用`-a`和`-l`参数:

```shell
ls -al
```

查看某一个目录的完整属性,而不是显示目录里面的文件属性:

```shell
ls -dl <目录名>
```

- 显示所有文件大小:

```shell
ls -asSh
```

### 变更文件所有者

切换到lilei用户,然后在/home/lilei目录下新建一个文件夹,命名为iphone11

```shell
su - lilei
pwd
touch iphone11
ls -alh iphone11
```

现在切换回shiyanlou用户,使用以下命令变更文件所有者为shiyanlou

```shell
cd /home/lilei
ls iphone11
sudo chown shiyanlou iphone11
```

### 修改文件权限

```shell
chmod go-rw iphone11
```

`g`、`o` 还有 `u` 分别表示 group（用户组）、others（其他用户） 和 user（用户），`+` 和 `-` 分别表示增加和去掉相应的权限

## 8.目录结构

![1](1.png)

### 目录路径

使用 `cd` 命令可以切换目录，在 Linux 里面使用 `.` 表示当前目录，`..` 表示上一级目录（**注意，我们上一节介绍过的，以 `.` 开头的文件都是隐藏文件，所以这两个目录必然也是隐藏的，你可以使用 `ls -a` 命令查看隐藏文件**），`-` 表示上一次所在目录，`～` 通常表示当前用户的 `home` 目录。使用 `pwd` 命令可以获取当前所在路径（绝对路径）。

进入上一级目录

```shell
cd ..
```

进入你的home目录:

```shell
cd ~
# 或者cd /home/<你的用户名>
```

使用pwd获取当前路径

```shell
pwd
```

绝对路径

关于绝对路径，简单地说就是以根" / "目录为起点的完整路径，以你所要到的目录为终点，表现形式如： `/usr/local/bin`，表示根目录下的 `usr` 目录中的 `local` 目录中的 `bin` 目录。

相对路径

相对路径，也就是相对于你当前的目录的路径，相对路径是以当前目录 `.` 为起点，以你所要到的目录为终点，表现形式如： `usr/local/bin` （这里假设你当前目录为根目录）。你可能注意到，我们表示相对路径实际并没有加上表示当前目录的那个 `.` ，而是直接以目录名开头，因为这个 `usr` 目录为 `/` 目录下的子目录，是可以省略这个 `.` 的（以后会讲到一个类似不能省略的情况）；如果是当前目录的上一级目录，则需要使用 `..` ，比如你当前目录为 `/home/shiyanlou` 目录下，根目录就应该表示为 `../../` ，表示上一级目录（ `home` 目录）的上一级目录（ `/` 目录）。

下面以`home`目录为起点,分别以绝对路径和相对路径的方式进入`/user/local/bin`目录:

```shell
# 绝对路径
cd /user/local/bin
# 相对路径
cd ../../user/local/bin
```

## 9.文件的基本操作

### 新建空白文件

使用`touch`命令来创建名为test的空白文件，因为在其他目录没有权限,所以需要先`cd ~`切换回shiyanlou用户的Home目录

```shell
cd ~
touch test
```

### 新建空白目录

使用`mkdir`命令可以创建一个空目录，也可以同时指定创建目录的权限属性

创建名为"mydir"的空目录:

```shell
mkdir mydir
```

使用-p参数，同时创建父目录(如果不存在该父目录)，如下我们同时创建一个多级目录:

```shell
mkdir -p father/son/grandson
```

需要注意的是，若当前目录已经创建了一个 test 文件，再使用 `mkdir test` 新建同名的文件夹，系统会报错文件已存在。这符合 Linux 一切皆文件的理念。

若当前目录存在一个 test 文件夹，则 `touch` 命令，则会更改该文件夹的时间戳而不是新建文件。

### 复制文件

使用 `cp` 命令（copy）复制一个文件到指定目录。

将之前创建的 `test` 文件复制到 `/home/shiyanlou/father/son/grandson` 目录中：

```shell
cp test father/son/grandson
```

### 复制目录

复制目录需要加上-r或-R参数,表示递归复制

```shell
cd /home/shiyanlou
mkdir family
cp -r father family
```

### 删除文件

使用 `rm`（remove files or directories）命令删除一个文件：

```shell
rm test
```

忽略提示直接删除只读权限的文件,可以使用`-f`参数强制删除:

```shell
rm -f test
```

### 删除目录

跟复制目录一样，要删除一个目录，也需要加上`-r`或者`-R`参数:

```shell
rm -r family
```

遇到权限不足删除不了的目录也可以和删除文件一样加上 `-f` 参数：

```shell
rm -rf family
```

### 移动文件

使用 `mv`（move or rename files）命令移动文件（剪切）。命令格式是 `mv 源目录文件 目的目录`。

```shell
mkdir Documents
touch file1
mv file1 Documents
```

### 重命名文件

`mv` 命令除了能移动文件外，还能给文件重命名。命令格式为 `mv 旧的文件名 新的文件名`。

```shell
mv file1 myfile
```

### 批量重命名

`rename` 命令并不是内置命令，若提示无该命令可以使用 `sudo apt-get install rename` 命令自行安装。而且它需要用perl正则表达式来作为参数

```shell
cd /home/shiyanlou/

# 使用通配符批量创建 5 个文件:
touch file{1..5}.txt

# 批量将这 5 个后缀为 .txt 的文本文件重命名为以 .c 为后缀的文件:
rename 's/\.txt/\.c/' *.txt

# 批量将这 5 个文件，文件名和后缀改为大写:
rename 'y/a-z/A-Z/' *.c
```

### 使用cat,tac和nl命令查看文件

前两个命令都是用来打印文件内容到标准输出（终端），其中 `cat` 为正序显示，`tac` 为倒序显示。

比如我们要查看之前从 `/etc` 目录下拷贝来的 `passwd` 文件：

```shell
cd /home/shiyanlou
cp /etc passwd passwd
cat passwd
```

可以加上-n参数显示行号:

```shell
cat -n passwd
```

`nl`命令，添加行号并打印常用的几个参数:

```shell
-b : 指定添加行号的方式，主要有两种：
    -b a:表示无论是否为空行，同样列出行号("cat -n"就是这种方式)
    -b t:只列出非空行的编号并列出（默认为这种方式）
-n : 设置行号的样式，主要有三种：
    -n ln:在行号字段最左端显示
    -n rn:在行号字段最右边显示，且不加 0
    -n rz:在行号字段最右边显示，且加 0
-w : 行号字段占用的位数(默认为 6 位)
```

### 使用more和less命令分页查看文件

使用`more`命令打开`passwd`文件:

```shell
more passwd
```

打开后默认只显示一屏内容，终端底部显示当前阅读的进度。可以使用 `Enter` 键向下滚动一行，使用 `Space` 键向下滚动一屏，按下 `h` 显示帮助，`q` 退出。

### 使用head和tail命令查看文件

因为系统新增加一个用户，会将用户的信息添加到 passwd 文件的最后，那么这时候我们就可以使用 `tail` 命令了：

```shell
tail /etc/passwd
```

甚至更直接的只看一行， 加上 -n 参数，后面紧跟行数：

```shell
tail -n 1 /etc/passwd
```

关于 `tail` 命令，不得不提的还有它一个很牛的参数 `-f`，这个参数可以实现不停地读取某个文件的内容并显示。这可以让我们动态查看日志，达到实时监视的目的。

### 查看文件类型

使用`file`命令查看文件的类型:

```shell
file /bin/ls
```

## 10.环境变量

### 创建一个变量

使用 `declare` 命令创建一个变量名为 tmp 的变量：

```shell
declare tmp
```

使用 `=` 号赋值运算符，将变量 tmp 赋值为 shiyanlou。注意，与其他语言不同的是， Shell 中的赋值操作，`=` 两边不可以输入空格，否则会报错。

```shell
tmp=shiyanlou
```

读取变量的值，使用 `echo` 命令和 `$` 符号（**$ 符号用于表示引用一个变量的值，初学者经常忘记输入**）:

```shell
echo $tmp
```

| 命令   | 说明                                                         |
| ------ | ------------------------------------------------------------ |
| set    | 显示当前 Shell 所有变量，包括其内建环境变量（与 Shell 外观等相关），用户自定义变量及导出的环境变量。 |
| env    | 显示与当前用户相关的环境变量，还可以让命令在指定环境中运行。 |
| export | 显示从 Shell 中导出成环境变量的变量，也能通过它将自定义变量导出为环境变量。 |

![image-20220728162209904](Typora\typora-user-images\image-20220728162209904.png)

### 

注意:`PATH`里面的路径是以`:`作为分隔符的，所以我们可以这样添加自定义路径(使用绝对路径):

```shell
PATH=$PATH:/home/shiyanlou/mybin
```

#### 查看PATH环境变量的内容

```shell
echo $PATH
```

创建一个C语言hello world程序:

```
cd /home/shiyanlou
gedit hello_world.c
```

输入以下内容

```c
#include<stdio.h>

int main(void)
{
	printf("hello world!\n");
	return 0;
}
```

保存后使用gcc生成可执行文件:

```shell
gcc -o hello_world hello_world.c
```

在 `/home/shiyanlou` 家目录创建一个 `mybin` 目录，并将上述  `hello_world` 文件移动到其中：

```sh
cd /home/shiyanlou
mkdir mybin
mv hello_shell.sh hello_world mybin/
```

现在你可以在`mybin`目录中分别运行你刚刚创建的两个程序:

```shell
cd mybin
./hello_world
```

|          变量设置方式          |                     说明                     |
| :----------------------------: | :------------------------------------------: |
|      `${变量名#匹配字串}`      | 从头向后开始匹配，删除符合匹配字串的最短数据 |
|     `${变量名##匹配字串}`      | 从头向后开始匹配，删除符合匹配字串的最长数据 |
|      `${变量名%匹配字串}`      | 从尾向前开始匹配，删除符合匹配字串的最短数据 |
|     `${变量名%%匹配字串}`      | 从尾向前开始匹配，删除符合匹配字串的最长数据 |
| `${变量名/旧的字串/新的字串}`  |    将符合旧字串的第一个字串替换为新的字串    |
| `${变量名//旧的字串/新的字串}` |     将符合旧字串的全部字串替换为新的字串     |

#### 变量删除

使用`unset`命令删除一个环境变量

```shell
unset mypath
```

#### 搜索文件(常用的搜索命令有whereis,which,find,locate)

- #### whereis简单快速

  ```
  whereis who
  whereis find
  ```

  whereis 只能搜索二进制文件（-b），man 帮助文件（-m）和源代码文件（-s）。

- #### locate快而全

  注意这个命令也不是内置的命令，在部分环境中需要手动安装，然后执行更新。

  ```shell
  sudo apt-get update
  sudo apt-get install locate
  sudo updatedb
  ```

  查找`/etc`下所有以sh开头的文件:

```shell
locate /etc/sh
```

如果想只统计数目可以加上 `-c` 参数，`-i` 参数可以忽略大小写进行查找，`whereis` 的 `-b`、`-m`、`-s` 同样可以使用。

- #### `which`小而精

  我们可以看到某个系统命令是否存在以及执行的到底是哪一个地方的命令

```shell
which man
which nginx
which ping
```

- #### `find`精而细

`find` 应该是这几个命令中最强大的了，它不但可以通过文件类型、文件名进行查找而且可以根据文件的属性（如文件的时间戳，文件的权限等）进行搜索。

与时间相关的命令参数：

| 参数     | 说明                   |
| -------- | ---------------------- |
| `-atime` | 最后访问时间           |
| `-ctime` | 最后修改文件内容的时间 |
| `-mtime` | 最后修改文件属性的时间 |

- `-mtime n`：n 为数字，表示为在 n 天之前的“一天之内”修改过的文件
- `-mtime +n`：列出在 n 天之前（不包含 n 天本身）被修改过的文件
- `-mtime -n`：列出在 n 天之内（包含 n 天本身）被修改过的文件
- `-newer file`：file 为一个已存在的文件，列出比 file 还要新的文件名

列出home目录中,当天(24小时之内)有改动的文件:

```shell
find ~ -mtime 0
```

列出用户家目录下比/etc目录新的的文件:

```shell
find ~ -newer /etc
```

## 11.文件打包

| 文件后缀名 | 说明                      |
| ---------- | ------------------------- |
| *.zip      | zip程序打包压缩的文件     |
| *.7z       | 7zip程序压缩的文件        |
| *.tar      | tar程序打包，未压缩的文件 |

### zip打包工具


```shell
# 使用zip打包文件夹(注意输入完整的参数和路径):
cd /home/shiyanlou
zip -r -q -o shiyanlou.zip /home/shiyanlou/Desktop
du -h shiyanlou.zip
file shiyanlou.zip
```

第一行命令中，`-r` 参数表示递归打包包含子目录的全部内容，`-q` 参数表示为安静模式，即不向屏幕输出信息，`-o`，表示输出文件，需在其后紧跟打包输出文件名。

- 设置压缩级别为9和1(9最大，1最小)，重新打包:

  ```shell
  zip -r -9 -q -o shiyanlou_9.zip /home/shiyanlou/Desktop -x ~/*.zip
  zip -r -1 -q -o shiyanlou_1.zip /home/shiyanlou/Desktop -x ~/*.zip
  ```

  1 表示最快压缩但体积大，9 表示体积最小但耗时最久。最后那个 `-x` 是为了排除我们上一次创建的 zip 文件，否则又会被打包进这一次的压缩文件中。

- 使用`du`命令分别查看默认压缩级别、最低、最高压缩等级及未压缩的文件的大小:

  ```shell
  du -h -d 0 *.zip ~ | sort
  ```

  `-h`可读的

  `-d`查看文件的深度

- 创建加密zip包

  使用`-e`参数可以创建加密压缩包

```shell
zip -r -e -o shiyanlou_encryption.zip /home/shiyanlou/Desktop
```

- 使用unzip命令解压缩


将shiyanlou.zip解压到当前目录:

```shell
unzip shiyanlou.zip
```

使用安静模式，将文件解压到指定目录：

```shell
unzip -q shiyanlou.zip -d ziptest
```

上述指定目录不存在，将会自动创建。如果你不想解压只想查看压缩包的内容你可以使用 `-l` 参数：

```shell
unzip -l shiyanlou.zip
```

使用-O(大写字母O)参数指定编码类型:

```shell
unzip -O GBK 中文压缩文件.zip
```

### tar打包工具

- 创建一个tar包

  ```shell
  cd /home/shiyanlou
  tar -P -cf shiyanlou.tar /home/shiyanlou/Desktop
  ```

  上面命令中，`-P` 保留绝对路径符，`-c` 表示创建一个 tar 包文件，`-f` 用于指定创建的文件名，注意文件名必须紧跟在 `-f` 参数之后，比如不能写成 `tar -fc shiyanlou.tar`，可以写成 `tar -f shiyanlou.tar -c ~`。你还可以加上 `-v` 参数以可视的的方式输出打包的文件。

- 解包一个文件(`-x`参数)到指定路径的**已存在**目录(`-c`参数)：

  ```shell
  mkdir tardir
  tar -xf shiyanlou.tar -C tardir
  ```

- 只查看不解包文件`-t`参数:

  ```shell
  tar -tf shiyanlou.tar
  ```

- 保留文件属性和跟随链接（符号链接或软链接），有时候我们使用 tar 备份文件当你在其他主机还原时希望保留文件的属性（`-p` 参数）和备份链接指向的源文件而不是链接本身（`-h` 参数）：

  ```shell
  tar -cphf etc.tar /etc
  ```

  | 压缩文件格式 | 参数 |
  | :----------: | :--: |
  |  `*.tar.gz`  | `-z` |
  |  `*.tar.xz`  | `-J` |
  |  `*tar.bz2`  | `-j` |

## 12.磁盘管理

- #### 查看磁盘和目录的容量

  使用`df`命令查看磁盘的容量

  ```shell
  df
  ```

  或者使用`df-h`命令查看

  ```shell
  df -h
  ```

- #### 使用du命令查看目录的容量

  ```shell
  # 默认同样以块的大小展示
  du
  # 加上'-h'参数,以更加易读的方式展示
  du -h
  ```

  `-d`参数指定查看目录的深度

  ```shell
  # 只查看1级目录的信息
  du -h -d 0 ~
  # 查看2级
  du -h -d 1 ~
  ```

  常用参数

  ```shell
  du -h # 同--human-readable 以K,M,G为单位，提高信息的可读性
  du -a # 显示所有文件的大小
  du -s # 仅显示总计，只列出最后加总的值
  ```

  ### 创建虚拟硬盘

  - #### `dd`命令简介

  `dd` 默认从标准输入中读取，并写入到标准输出中，但可以用选项 `if`（input file，输入文件）和 `of`（output file，输出文件）改变。

  使用`dd`命令从标准输入读入用户的输入到标准输出或者一个文件中:

```shell
# 输出到文件
dd of=test bs=10 count=1
# 或者 dd if=/dev/stdin of=test bs=10 count=1
#输出到标准输出
dd if=/dev/stdin of=/dev/stdout bs=10 count=1
# 在打完这个命令后，继续在终端打字，作为你的输入
```

​	上述命令从标准输入设备读入用户输入（缺省值，所以可省略）然后输出到 test 文件，`bs`（block size）用于指定块大小（缺省单位为 	Byte，也可为其指定如 `K`，`M`，`G` 等单位），`count` 用于指定块数量。如上图所示，我指定只读取总共 10 个字节的数据，当我输入	了 `hello shiyanlou` 之后加上空格回车总共 16 个字节（一个英文字符占一个字节）内容，显然超过了设定大小。使用 `du` 和 `cat` 10 	个字节（那个黑底百分号表示这里没有换行符），而其他的多余输入将被截取并保留在标准输入。

`dd`命令在拷贝的同时还可以实现数据转换，下面例子将输出的英文字符转换为大写再写入文件:

```shell
dd if=/dev/stdin of=test bs=10 count=1 conv=ucase
```

- #### 使用`dd`命令创建虚拟镜像文件

  从`/dev/zero`设备创建一个容量为256M的空文件：

  ```shell
  dd if=/dev/zero of=virtual.img bs=1M count=256
  du -h virtual.img
  ```

- #### 使用`mkfs`命令格式化磁盘

我们可以简单的使用下面的命令来将我们的虚拟磁盘镜像格式化为 `ext4` 文件系统：

```shell
sudo mkfs.ext4 virtual.img
```

可以看到实际 `mkfs.ext4` 是使用 `mke2fs` 来完成格式化工作的。

- #### 使用mount命令挂载磁盘到目录树上

使用`mount`来查看下主机已经挂载的文件系统:

```shell
sudo mount
```

输出的结果中每一行表示一个设备或虚拟设备，每一行最前面是设备名，然后是 on 后面是挂载点，type 后面表示文件系统类型，再后面是挂载选项（比如可以在挂载时设定以只读方式挂载等等）。

mount命令的一般格式如下:

```shell
mount [options] [source] [directory]
```

一些常用操作:

```shell
mount [-o [操作选项]] [-t 文件系统类型] [-w|--rw|--ro] [文件系统源] [挂载点]
```

- #### 使用`umount`命令卸载已挂载磁盘

```shell
# 命令格式 sudo umount 已挂载设备名或者挂载点，如：
sudo umount /mnt
```

- #### 使用fdisk为磁盘分区

```shell
# 查看硬盘分区表信息
sudo fdisk -l
```

```shell
# 进入磁盘分区模式
sudo fdisk virtual.img
```

```shell
# 解除
sudo losetup /dev/loop0 virtual.img
# 如果提示设备忙你也可以使用其它的回环设备，"ls /dev/loop*"参看所有回环设备

# 解除设备关联
sudo losetup -d /dev/loop0
```

## 13.Linux下的帮助命令

#### `help`命令

进入bash中，在bash中内置有该命令:

```shell
bash
```

使用`help`命令:

```shell
help ls
```

因为 help 命令是用于显示 shell 内建命令的简要帮助信息。

如果是外部命令可以使用`--help`参数，就能得到相应的帮助:

```shell
ls --help
```

#### `man`命令

```shell
man ls
```

​		得到的内容比用 help 更多更详细，而且 man 没有内建与外部命令的区分，因为 man 工具是		显示系统手册页中的内容，也就是一本电子版的字典，这些内容大多数都是对命令的解释信		息，还有一些相关的描述。

#### `info`命令

```shell
# 安装 info
sudo apt-get update
sudo apt-get install info
# 查看 ls 命令的 info
info ls
```

#### `crontab`命令简介

crontab 命令从输入设备读取指令，并将其存放于 crontab 文件中，以供之后读取和执行。通常，crontab 储存的指令被守护进程激活，crond 为其守护进程，crond 常常在后台运行，每一分钟会检查一次是否有预定的作业需要执行。

通过 crontab 命令，我们可以在固定的间隔时间执行指定的系统指令或 shell 脚本。时间间隔的单位可以是分钟、小时、日、月、周的任意组合。

通过下面的命令来添加一个计划任务:

```shell
crontab -e
```

通过这样的一个例子来完成一个任务的添加，在文档的最后一排加上这样一排命令，该任务是每分钟我们会在/home/shiyanlou 目录下创建一个以当前的年月日时分秒为名字的空白文件

```shell
*/1 * * * * toouch /home/shiyanlou/$(date+\%Y\%m\%d\%H\%M\%S)
```

查看我们添加的任务:

```shell
crontab -l
```

删除任务:

```shell
crontab -r
```

在`/etc`目录中,`cron`相关的目录有下面几个并且每个目录的作用：

1. `/etc/cron.daily`，目录下的脚本会每天执行一次，在每天的 6 点 25 分时运行；
2. `/etc/cron.hourly`，目录下的脚本会每个小时执行一次，在每小时的 17 分钟时运行；
3. `/etc/cron.monthly`，目录下的脚本会每月执行一次，在每月 1 号的 6 点 52 分时运行；
4. `/etc/cron.weekly`，目录下的脚本会每周执行一次，在每周第七天的 6 点 47 分时运行；

## 14.命令执行顺序控制与管道

使用 `which` 来查找是否安装某个命令，如果找到就执行该命令，否则什么也不做：

```shell
which cowsay>/dev/null && cowsay -f head-in ohch~
```

上面的 `&&` 就是用来实现选择性执行的，它表示如果前面的命令执行结果（不是表示终端输出的内容，而是表示命令执行状态的结果）返回 0 则执行后面的，否则不执行，你可以从 `$?` 环境变量获取上一次命令的返回结果：

```shell
which cowsay
echo $?
which cat
echo $?
```

同样 Shell 也有一个 `||`，它们的区别就在于，shell 中的这两个符号除了也可用于表示逻辑与和或之外，就是可以实现这里的命令执行顺序的简单控制。`||` 在这里就是与 `&&` 相反的控制效果，当上一条命令执行结果为 `≠0(\$?≠0)` 时则执行它后面的命令：

```shell
which cowsay>/dev/null || echo "cowsay has not been install,please run 'sudo apt-get install cowsay' to install"
```

### 管道

管道是一种通信机制，通常用于进程间的通信（也可通过 socket 进行网络通信），它表现出来的形式就是将前面每一个进程的输出（stdout）直接作为下一个进程的输入（stdin）。管道又分为匿名管道和具名管道

查看`/etc`目录下有哪些文件和目录,使用`ls`命令来查看:

```shell
ls -al /etc
```

使用管道:

```shell
ls -al /etc | less
```

通过管道将前一个命令(`ls`)的输出作为下一个命令(`less`)的输入，然后就可以一行一行地看。

####  cut命令,打印每一行的某一字段

打印`/etc/passwd`文件中以`:`为分隔符的第1个字段和第6个字段分别用户名和其家目录:

```shell
cut /etc/passwd -d ':' -f 1,6
```

| 参 数 |                             详解                             |
| :---: | :----------------------------------------------------------: |
|  -b   | 以字节为单位进行分割。这些字节位置将忽略多字节字符边界，除非也指定了 -n 标志。 |
|  -c   |                    以字符为单位进行分割。                    |
|  -d   |                 自定义分隔符，默认为制表符。                 |
|  -f   |               与-d一起使用，指定显示哪个区域。               |
|  -n   | 取消分割多字节字符。仅和 -b 标志一起使用。如果字符的最后一个字节落在由 -b 标志的 List 参数指示的范围之内，该字符将被写出；否则，该字符将被排除 |

#### grep命令,在文本中或stdin中查找匹配字符串

`grep`命令的一般形式为:

```shell
grep [命令选项]... 用于匹配的表达式 [文件]...
```

#### wc命令,简单小巧的计数工具

wc 命令用于统计并输出一个文件中行、单词和字节的数目，比如输出 `/etc/passwd` 文件的统计信息：

```shell
wc /etc/passwd
```

分别只输出行数、单词数、字节数、字符数和输入文本中最长一行的字节数:

```shell
# 行数
wc -l /etc/passwd
# 单词数
wc -w /etc/passwd
# 字节数
wc -c /etc/passwd
# 字符数
wc -m /etc/passwd
# 最长行字节数
wc -L /etc/passwd
```

结合管道来操作一下，下面统计/etc下面所有目录数；

```shell
ls -dl /dev/*/ |wc -l
```

#### sort排序命令

默认为字典排序

```shell
cat /etc/passwd | sort
```

反转排序:

```shell
cat /etc/passwd | sort -r
```

按特定字段排序

```shell
cat /etc/passwd | sort -t':' 
```

上面的`-t`参数用于指定字段的分隔符，这里是以":"作为分隔符；`-k 字段号`用于指定对哪一个字段进行排序。这里`/etc/passwd`文件的第三个字段为数字，默认情况下是以字典序排序的，如果要按照数字排序就要加上`-n`参数：

```
cat /etc/passwd | sort -t':' -k 3 -n
```

####  uniq去重命令

`uniq`命令可以用于过滤或输出重复行

- 过滤重复行

  使用`history`命令查看最近执行过的命令,使用命令去掉命令后面的参数然后去掉重复的命令:

  ```shell
  history | cut -c 8- | cut -d ' ' -f 1 | uniq
  ```

  ```shell
  history | cut -c 8- | cut -d ' ' -f 1 | sort | uniq
  # 或者
  history | cut -c 8- | cut -d ' ' -f 1 | sort -u
  ```

- 输出重复行

  ```shell
  # 输出重复过的行（重复的只输出一个）及重复次数
  history | cut -c 8- | cut -d ' ' -f 1 | sort | uniq -dc
  # 输出所有重复的行
  history | cut -c 8- | cut -d ' ' -f 1 | sort | uniq -D
  ```

## 15.文本处理命令

### tr命令

tr 命令可以用来删除一段文本信息中的某些文字。或者将其进行转换。

使用方式:

```shell
tr [option]...SET1 [SET2]
```

常用的选项有:

| 选项 |                             说明                             |
| :--: | :----------------------------------------------------------: |
| `-d` | 删除和 set1 匹配的字符，注意不是全词匹配也不是按字符顺序匹配 |
| `-s` |         去除 set1 指定的在输入文本中连续并重复的字符         |

操作实例:

```shell
# 删除 "hello shiyanlou" 中所有的'o'，'l'，'h'
echo 'hello shiyanlou' | tr -d 'olh'
# 将"hello" 中的ll，去重为一个l
echo 'hello' | tr -s 'l'
# 将输入文本，全部转换为大写或小写输出
echo 'input some text here' | tr '[:lower:]' '[:upper:]'
# 上面的'[:lower:]' '[:upper:]'你也可以简单的写作'[a-z]' '[A-Z]'，当然反过来将大写变小写也是可以的
```

### col命令

col 命令可以将`Tab`换成对等数量的空格键，或反转这个操作。

使用方式:

```shell
col [option]
```

常用的选项有:                                     

| 选项 | 说明                          |
| ---- | ----------------------------- |
| `-x` | 将`Tab`转换为空格             |
| `-h` | 将空格转换为`Tab`（默认选项） |

操作实例:

```shell
# 查看 /etc/protocols 中的不可见字符，可以看到很多 ^I ，这其实就是 Tab 转义成可见字符的符号
cat -A /etc/protocols
# 使用 col -x 将 /etc/protocols 中的 Tab 转换为空格，然后再使用 cat 查看，你发现 ^I 不见了
cat /etc/protocols | col -x | cat -A
```

### join命令

join命令就是用于将两个文件中包含相同内容的那一行合并在一起

使用方式:

```shell
join [option]...file1 file2
```

常用的选项有:

| 选项 |                         说明                         |
| :--: | :--------------------------------------------------: |
| `-t` |                指定分隔符，默认为空格                |
| `-i` |                   忽略大小写的差异                   |
| `-1` | 指明第一个文件要用哪个字段来对比，默认对比第一个字段 |
| `-2` | 指明第二个文件要用哪个字段来对比，默认对比第一个字段 |

操作实例

```shell
cd /home/shiyanlou
# 创建两个文件
echo '1 hello' > file1
echo '1 shiyanlou' > file2
join file1 file2
# 将 /etc/passwd 与 /etc/shadow 两个文件合并，指定以':'作为分隔符
sudo join -t':' /etc/passwd /etc/shadow
# 将 /etc/passwd 与 /etc/group 两个文件合并，指定以':'作为分隔符，分别比对第4和第3个字段
sudo join -t':' -1 4 /etc/passwd -2 3 /etc/group
```

### paste命令

它是在不对比数据的情况下，简单地将多个文件合并一起，以`Tab`隔开。

使用方式:

```
paste [option] file...
```

常用选项:

| 选项 |             说明             |
| :--: | :--------------------------: |
| `-d` | 指定合并的分隔符，默认为 Tab |
| `-s` | 不合并到一行，每个文件为一行 |

```shell
echo hello > file1
echo shiyanlou > file2
echo www.shiyanlou.com > file3
paste -d ':' file1 file2 file3
paste -s file1 file2 file3
```

## 16.数据流重定向

Linux默认提供三个特殊设备,用于终端的显示和输出，分别为`stdin`(标准输入,对应于你在终端的输入)，`stdout`(标准输出,对应于终端的输出)，stderr(标准错误输出，对应于终端的输出)

| 文件描述符 |   设备文件    |   说明   |
| :--------: | :-----------: | :------: |
|    `0`     | `/dev/stdin`  | 标准输入 |
|    `1`     | `/dev/stdout` | 标准输出 |
|    `2`     | `/dev/stderr` | 标准错误 |

默认使用终端的标准输入作为命令的输入和标准输出作为命令的输出：

```shell
cat #按Crtl+C退出
```

将 cat 的连续输出（heredoc 方式）重定向到一个文件：

```shell
mkdir Documents
cat > Documents/test.c <<EOF
#include <stdio.h>

int main()
{
    printf("hello world\n");
    return 0;
}

EOF
```

将一个文件作为命令的输入，标准输出作为命令的输出：

```shell
cat Documents/test.c
```

将 echo 命令通过管道传过来的数据作为 cat 命令的输入，将标准输出作为命令的输出：

```shell
echo 'hi' | cat
```

将 echo 命令的输出从默认的标准输出重定向到一个普通文件：

```shell
echo 'hello shiyanlou' > redirect
cat redirect
```

注意:**管道默认是连接前一个命令的输出到下一个命令的输入**，**而重定向通常是需要一个文件来建立两个命令的连接**

### 使用tee命令同时重定向到多个文件

如果你需要将输出重定向到文件，也需要将信息打印在终端。那么你可以使用 `tee` 命令来实现：

```shell
echo 'hello shiyanlou' | tee hello
```

### 永久重定向

使用`exec`命令实现永久重定向，`exec`命令的作用是使用指定的命令替换当前的Shell，即使用一个进程替换当前进程，或者指定新的重定向:

```shell
# 先开启一个子 Shell
zsh
# 使用exec替换当前进程的重定向，将标准输出重定向到一个文件
exec 1>somefile
# 后面你执行的命令的输出都将被重定向到文件中，直到你退出当前子shell，或取消exec的重定向（后面将告诉你怎么做）
ls
exit
cat somefile
```

### 创建输出文件描述符

使用下面命令查看当前shell进程中打开的文件描述符:

```shell
cd /dev/fd;ls -Al
```

同样使用`exec`命令可以创建新的文件描述符:

```shell
zsh
exec 3>somefile
# 先进入目录，再查看，否则你可能不能得到正确的结果，然后再回到上一次的目录
cd /dev/fd/;ls -Al;cd -
# 注意下面的命令>与&之间不应该有空格，如果有空格则会出错
echo "this is test" >&3
cat somefile
exit
```

### 关闭文件描述符

```shell
exec 3>&-
cd /dev/fd;ls -Al;cd -
```

利用`/dev/null`屏蔽命令的输出:

```shell
cat Documents/test.c 1>/dev/null 2>&1
```

### 使用xargs分割参数列表:

```shell
cut -d: -f1 < /etc/passwd | sort | xargs echo
```

上面这个命令用于将 `/etc/passwd` 文件按 `:` 分割取第一个字段排序后，使用 `echo` 命令生成一个列表。

#### 挑战:历史命令

**目标**:1.处理文本文件`/home/shiyanlou/data1`

2.将结果写入`/home/shiyanlou/result`

3.结果包含三行内容，每行内容都是出现的次数和命令名称,如"100 ls"

**提示**:1.`cut 文件 -c number1-` 从number1截取到最后

2.`sort -n` 比较readable的数字 `-r` 逆序排序

3.`head -number` 截取前number行

4.`uniq -dc`去重并计数

5.操作过程使用管道

```shell
cd /home/shiyanlou
cat data1 |cut -c 8-|sort|uniq -dc|sort -rn | head -3 > /home/shiyanlou/result 
```

记得要先sort再uniq，因为uniq去重时是检测相邻有没有重复，如果不排序将会出现很多重复计数的情况

## 17.正则表达式基本命令

### 基本语法

一个正则表达式通常被称为一个模式（**pattern**），为用来描述或者匹配一系列符合某个句法规则的字符串。

#### 选择

`|` 竖直分隔符表示选择，例如 `boy|girl` 可以匹配 `boy` 或者 `girl`。

#### 数量限定

**号+加号?问号*如果在一个模式中不加数量限定符则表示出现一次且仅出现一次：

- `+` 表示前面的字符必须出现至少一次(1 次或多次)，例如 `goo+gle` 可以匹配 `gooogle`，`goooogle` 等；
- `?` 表示前面的字符最多出现一次（0 次或 1 次），例如，`colou?r`，可以匹配 `color` 或者 `colour`;
- `*` 星号代表前面的字符可以不出现，也可以出现一次或者多次（0 次、或 1 次、或多次），例如，`0*42` 可以匹配 42、042、0042、00042 等。

#### 范围和优先级

`()` 圆括号可以用来定义模式字符串的范围和优先级，这可以简单的理解为是否将括号内的模式串作为一个整体。例如，`gr(a|e)y` 等价于 `gray|grey`，（这里体现了优先级，竖直分隔符用于选择 `a` 或者 `e` 而不是 `gra` 和 `ey`），`(grand)?father` 匹配 `father` 和 `grandfather`（这里体现了范围，`?` 将圆括号内容作为一个整体匹配）。

#### 语法(部分)

|    字符     |                             描述                             |
| :---------: | :----------------------------------------------------------: |
|     `\`     | **将下一个字符标记为一个特殊字符、或一个原义字符。** 例如 `n` 匹配字符 `n`。`\n` 匹配一个换行符。序列 `\\` 匹配 `\` 而 `\(` 则匹配 `(`。 |
|     `^`     |                **匹配输入字符串的开始位置。**                |
|     `$`     |                **匹配输入字符串的结束位置。**                |
|    `{n}`    | n 是一个非负整数。**匹配确定的 n 次**。例如 `o{2}` 不能匹配 `Bob` 中的 `o`，但是能匹配 `food` 中的两个 `o`。 |
|   `{n,}`    | n 是一个非负整数。**至少匹配 n 次**。例如 `o{2,}` 不能匹配 `Bob` 中的 `o`，但能匹配 `foooood` 中的所有 `o`。`o{1,}` 等价于 `o+`。`o{0,}` 则等价于 `o*`。 |
|   `{n,m}`   | m 和 n 均为非负整数，其中 `n<=m`。**最少匹配 n 次且最多匹配 m 次**。例如，`o{1,3}` 将匹配 `fooooood` 中的前三个 `o`。`o{0,1}` 等价于 `o?`。请注意在逗号和两个数之间不能有空格。 |
|     `*`     | **匹配前面的子表达式零次或多次**。例如，`zo*` 能匹配 `z`、`zo` 以及 `zoo`。`*` 等价于 `{0,}`。 |
|     `+`     | **匹配前面的子表达式一次或多次**。例如，`zo+` 能匹配 `zo` 以及 `zoo`，但不能匹配 `z`。`+` 等价于 `{1,}`。 |
|     `?`     | **匹配前面的子表达式零次或一次**。例如，`do(es)?` 可以匹配 `do` 或 `does` 中的 `do`。`?` 等价于 `{0,1}`。 |
|     `?`     | 当该字符紧跟在任何一个其他限制符（`*`，`+`，`?`，`{n}`，`{n,}`，`{n,m}`）后面时，匹配模式是非贪婪的。非贪婪模式尽可能少的匹配所搜索的字符串，而默认的贪婪模式则尽可能多的匹配所搜索的字符串。例如，对于字符串 `oooo`，`o+?` 将匹配单个 `o`，而 `o+` 将匹配所有 `o`。 |
|     `.`     | **匹配除 `\n` 之外的任何单个字符**。要匹配包括 `\n` 在内的任何字符，请使用类似 `(.｜\n)` 的模式。 |
| `(pattern)` | **匹配 pattern 并获取这一匹配的子字符串**。该子字符串用于向后引用。要匹配圆括号字符，请使用 `\(` 和 `\)`。 |
|   x ｜ y    | **匹配 x 或 y**。例如，“z ｜ food”能匹配 `z` 或 `food`。“(z ｜ f)ood”则匹配 `zood` 或 `food`。 |
|   `[xyz]`   | 字符集合（character class）。**匹配所包含的任意一个字符**。例如，`[abc]` 可以匹配 `plain` 中的 `a`。其中特殊字符仅有反斜线 `\` 保持特殊含义，用于转义字符。其它特殊字符如星号、加号、各种括号等均作为普通字符。脱字符^如果出现在首位则表示负值字符集合；如果出现在字符串中间就仅作为普通字符。**连字符 `-` 如果出现在字符串中间表示字符范围描述；如果出现在首位则仅作为普通字符。** |
|  `[^xyz]`   | 排除型（negate）字符集合。**匹配未列出的任意字符。**例如，`[^abc]` 可以匹配 `plain` 中的 `plin`。 |
|   `[a-z]`   | 字符范围。**匹配指定范围内的任意字符。**例如，`[a-z]` 可以匹配 `a` 到 `z` 范围内的任意小写字母字符。 |
|  `[^a-z]`   | 排除型的字符范围。**匹配任何不在指定范围内的任意字符**。例如，`[^a-z]` 可以匹配任何不在 `a` 到 `z` 范围内的任意字符。 |

#### 优先级

优先级为从上到下从左到右，依次降低:

|                运算符                 |     说明     |
| :-----------------------------------: | :----------: |
|                  `\`                  |    转义符    |
|      `()`，`(?:)`，`(?=)`，`[]`       | 括号和中括号 |
| `*`，`+`，`?`，`{n}`，`{n,}`，`{n,m}` |    限定符    |
|       `^`，`$`，`\` 任何元字符        | 定位点和序列 |
|                  \|                   |     选择     |

#### grep模式匹配命令

`grep`命令用于打印输出文本中匹配的模式串，它使用正则表达式作为模式匹配的条件。`grep`支持三种正则表达式引擎，分别用三个参数指定:

| 参数 |          说明           |
| :--: | :---------------------: |
|  -E  | POSIX扩展正则表达式,ERE |
|  -G  | POSIX基本正则表达式,BRE |
|  -P  |   Perl正则表达式,PCRE   |

grep常用参数:

|      参数      |                             说明                             |
| :------------: | :----------------------------------------------------------: |
|      `-b`      |                将二进制文件作为文本来进行匹配                |
|      `-c`      |                     统计以模式匹配的数目                     |
|      `-i`      |                          忽略大小写                          |
|      `-n`      |                   显示匹配文本所在行的行号                   |
|      `-v`      |                   反选，输出不匹配行的内容                   |
|      `-r`      |                         递归匹配查找                         |
|     `-A n`     | n 为正整数，表示 after 的意思，除了列出匹配行之外，还列出后面的 n 行 |
|     `-B n`     | n 为正整数，表示 before 的意思，除了列出匹配行之外，还列出前面的 n 行 |
| `--color=auto` |              将输出中的匹配项设置为自动颜色显示              |

### 使用基本正则表达式,BRE

- 位置

  查找`/etc/group`文件中以shiyanlou为开头的行

  ```shell
  grep 'shiaynlou' /etc/group
  grep '^shiyanlou' /etc/group
  ```

- 数量

  ```shell 
  # 将匹配以'z'开头以'o'结尾的所有字符串
  echo 'zero\nzo\nzoo' | grep 'z.*o'
  # 将匹配以'z'开头以'o'结尾，中间包含一个任意字符的字符串
  echo 'zero\nzo\nzoo' | grep 'z.o'
  # 将匹配以'z'开头，以任意多个'o'结尾的字符串
  echo 'zero\nzo\nzoo' | grep 'zo*'
  ```

- 选择

  ```shell 
  # grep默认是区分大小写的，这里将匹配所有的小写字母
  echo '1234\nabcd' | grep '[a-z]'
  # 将匹配所有的数字
  echo '1234\nabcd' | grep '[0-9]'
  # 将匹配所有的数字
  echo '1234\nabcd' | grep '[[:digit:]]'
  # 将匹配所有的小写字母
  echo '1234\nabcd' | grep '[[:lower:]]'
  # 将匹配所有的大写字母
  echo '1234\nabcd' | grep '[[:upper:]]'
  # 将匹配所有的字母和数字，包括0-9，a-z，A-Z
  echo '1234\nabcd' | grep '[[:alnum:]]'
  # 将匹配所有的字母
  echo '1234\nabcd' | grep '[[:alpha:]]'
  ```

  完整的特殊符号及说明:

  

| 特殊符号     |                             说明                             |
| ------------ | :----------------------------------------------------------: |
| `[:alnum:]`  |         代表英文大小写字母及数字，亦即 0-9，A-Z，a-z         |
| `[:alpha:]`  |            代表任何英文大小写字母，亦即 A-Z，a-z             |
| `[:blank:]`  |                代表空白键与 `[Tab]` 按键两者                 |
| `[:cntrl:]`  |     代表键盘上面的控制按键，亦即包括 CR，LF，Tab，Del...     |
| `[:digit:]`  |                    代表数字而已，亦即 0-9                    |
| `[:graph:]`  |     除了空白字节（空白键与 [Tab] 按键）外的其他所有按键      |
| `[:lower:]`  |                    代表小写字母，亦即 a-z                    |
| `[:print:]`  |                 代表任何可以被列印出来的字符                 |
| `[:punct:]`  | 代表标点符号（punctuation symbol），即：`"`，`'`，`?`，`!`，`;`，`:`，`#`，`$`... |
| `[:upper:]`  |                    代表大写字母，亦即 A-Z                    |
| `[:space:]`  |      任何会产生空白的字符，包括空格键，`[Tab]`，CR 等等      |
| `[:xdigit:]` | 代表 16 进位的数字类型，因此包括： 0-9，A-F，a-f 的数字与字节 |

```shell
# 排除字符
echo 'geek\ngood' | grep '[^o]'
```

### 使用扩展正则表达式,ERE

要通过`grep`使用扩展正则表达式需要加上`-E`参数,或使用`egrep`

- 数量

  ```shell
  # 只匹配"zo"
  echo 'zero\nzo\nzoo' | grep -E 'zo{1}'
  # 匹配以"zo"开头的所有单词
  echo 'zero\nzo\nzoo' | grep -E 'zo{1,}'
  ```

  *推荐掌握 `{n,m}` 即可 `+`，`?`，`*` 这几个不太直观，且容易弄混淆。*

- 选择

```shell
# 匹配"www.shiyanlou.com"和"www.google.com"
echo 'www.shiyanlou.com\nwww.baidu.com\nwww.google.com' | grep -E 'www\.(shiyanlou|google)\.com'
# 或者匹配不包含"baidu"的内容
echo 'www.shiyanlou.com\nwww.baidu.com\nwww.google.com' | grep -Ev 'www\.baidu\.com'
```

### sed常用参数

#### sed命令基本格式:

```shell
sed [参数]... [执行命令] [输入文件]...
# 形如：
$ sed -i 's/sad/happy/' test # 表示将test文件中的"sad"替换为"happy"
```

|     参数      |                             说明                             |
| :-----------: | :----------------------------------------------------------: |
|     `-n`      |    安静模式，只打印受影响的行，默认打印输入数据的全部内容    |
|     `-e`      | 用于在脚本中添加多个执行命令一次执行，在命令行中执行多个命令通常不需要加该参数 |
| `-f filename` |                指定执行 filename 文件中的命令                |
|     `-r`      |           使用扩展正则表达式，默认为标准正则表达式           |
|     `-i`      |       将直接修改输入文件内容，而不是打印到标准输出设备       |

#### sed执行命令格式:

```shell
[n1][,n2]command
[n1][~step]command
```

其中一些命令还可以在后面加上作用范围:

```shell
sed -i 's/sad/happy/g' test # g 表示全局范围
sed -i 's/sad/happy/4' test # 4 表示指定行中的第四个匹配字符串
```

其中n1,n2表示输入内容的行号，他们之间为，逗号则表示从n1到n2行，如果为~波浪号则表示从n1开始以step为步进的所有行；command为执行动作

| 命令 |                 说明                 |
| :--: | :----------------------------------: |
| `s`  |               行内替换               |
| `c`  |               整行替换               |
| `a`  |          插入到指定行的后面          |
| `i`  |          插入到指定行的前面          |
| `p`  | 打印指定行，通常与 `-n` 参数配合使用 |
| `d`  |              删除指定行              |

#### 打印指定行

```shell
# 打印2-5行
nl passwd | sed -n '2,5p'
# 打印奇数行
nl passwd | sed -n '1~2p'
```

#### 行内替换

```shell
# 将输入文本中"shiyanlou" 全局替换为"hehe"，并只打印替换的那一行，注意这里不能省略最后的"p"命令
# 可以结合正则表达式使用
sed -n 's/shiyanlou/hehe/gp' passwd
```

###  awk文本处理语言

#### awk常用操作

awk所有的操作都是基于pattern(模式)-action(动作)对来完成的，例如

```shell
pattern {action}
```

其中 pattern 通常是表示用于匹配输入的文本的“关系式”或“正则表达式”，action 则是表示匹配后将执行的动作。在一个完整 awk 操作中，这两者可以只有其中一个，如果没有 pattern 则默认匹配输入的全部文本，如果没有 action 则默认为打印匹配内容到屏幕。

#### awk命令基本格式

```shell
awk [-F fs] [-v var=value] [-f prog-file | 'program text'] [file...]xxxxxxxxxx awk [awk [-F fs] [-v var=value] [-f prog-file | 'program text'] [file...]]
```

其中 `-F` 参数用于预先指定前面提到的字段分隔符（还有其他指定字段的方式），`-v` 用于预先为 `awk` 程序指定变量，`-f` 参数用于指定 `awk` 命令要执行的程序文件，或者在不加 `-f` 参数的情况下直接将程序语句放在这里，最后为 `awk` 需要处理的文本输入，且可以同时输入多个文本文件。

使用awk将文本内容打印到终端:

```shell
# "quote>" 不用输入
awk '{
quote> print
quote> }' test
# 或者写到一行
awk '{print}' test
```

- 将 test 的第一行的每个字段单独显示为一行：

```shell
$ awk '{
> if(NR==1){
> print $1 "\n" $2 "\n" $3
> } else {
> print}
> }' test

# 或者
$ awk '{
> if(NR==1){
> OFS="\n"
> print $1, $2, $3
> } else {
> print}
> }' test
```

#### awk常用的内置变量

| 变量名     | 说明                                                         |
| ---------- | ------------------------------------------------------------ |
| `FILENAME` | 当前输入文件名，若有多个文件，则只表示第一个。如果输入是来自标准输入，则为空字符串 |
| `$0`       | 当前记录的内容                                               |
| `$N`       | N 表示字段号，最大值为`NF`变量的值                           |
| `FS`       | 字段分隔符，由正则表达式表示，默认为空格                     |
| `RS`       | 输入记录分隔符，默认为 `\n`，即一行为一个记录                |
| `NF`       | 当前记录字段数                                               |
| `NR`       | 已经读入的记录数                                             |
| `FNR`      | 当前输入文件的记录数，请注意它与 NR 的区别                   |
| `OFS`      | 输出字段分隔符，默认为空格                                   |
| `ORS`      | 输出记录分隔符，默认为 `\n`                                  |

## 18.Linux下软件安装

### 使用apt

`apt-get` 是用于处理 `apt`包的公用程序集，我们可以用它来在线安装、卸载和升级软件包等，下面列出一些 `apt-get` 包含的常用的一些工具：

|      工具      |                             说明                             |
| :------------: | :----------------------------------------------------------: |
|   `install`    |             其后加上软件包名，用于安装一个软件包             |
|    `update`    | 从软件源镜像服务器上下载/更新用于更新本地软件源的软件包列表  |
|   `upgrade`    | 升级本地可更新的全部软件包，但存在依赖问题时将不会升级，通常会在更新之前执行一次 `update` |
| `dist-upgrade` |             解决依赖关系并升级（存在一定危险性）             |
|    `remove`    | 移除已安装的软件包，包括与被移除软件包有依赖关系的软件包，但不包含软件包的配置文件 |
|  `autoremove`  |      移除之前被其他软件包依赖，但现在不再被使用的软件包      |
|    `purge`     |      与 remove 相同，但会完全移除软件包，包含其配置文件      |
|    `clean`     | 移除下载到本地的已经安装的软件包，默认保存在 `/var/cache/apt/archives/` |
|  `autoclean`   |                移除已安装的软件的旧版本软件包                |

下面是一些`apt-get`常用的参数：

|         参数         |                             说明                             |
| :------------------: | :----------------------------------------------------------: |
|         `-y`         | 自动回应是否安装软件包的选项，在一些自动化安装脚本中使用这个参数将十分有用 |
|         `-s`         |                           模拟安装                           |
|         `-q`         | 静默安装方式，指定多个 `q` 或者 `-q=#`，`#` 表示数字，用于设定静默级别，这在你不想要在安装软件包时屏幕输出过多时很有用 |
|         `-f`         |                      修复损坏的依赖关系                      |
|         `-d`         |                         只下载不安装                         |
|    `--reinstall`     |            重新安装已经安装但可能存在问题的软件包            |
| `--install-suggests` |             同时安装 APT 给出的建议安装的软件包              |

使用以下方式重新安装:

```shell
sudo apt-get --reinstall install <packa>
```

软件升级:

```shell
# 更新软件源
sudo apt-get update

# 升级没有依赖问题的软件包
sudo apt-get upgrade

# 升级并解决依赖关系
sudo apt-get dist-upgrade
```

软件卸载:

```shell
sudo apt-get remove w3m
```

或者执行以下命令:

```shell
# 不保留配置文件的移除
sudo apt-get purge w3m
# 或者
sudo apt-get --purge remove w3m
# 移除不再需要的被依赖的软件包
sudo apt-get autoremove
```

软件搜索:

```shell
sudo apt-cache search softname1 softname2 softname3……
```

`apt-cache` 命令则是针对本地数据进行相关操作的工具，`search` 顾名思义在本地的数据库中寻找有关 `softname1`，`softname2` 相关软件的信息。

###  使用dpkg

dpkg常用参数:

| 参数 |                       说明                        |
| :--: | :-----------------------------------------------: |
| `-i` |                  安装指定 deb 包                  |
| `-R` | 后面加上目录名，用于安装该目录下的所有 deb 安装包 |
| `-r` |          remove，移除某个已安装的软件包           |
| `-I` |              显示 `deb` 包文件的信息              |
| `-s` |               显示已安装软件的信息                |
| `-S` |                搜索已安装的软件包                 |
| `-L` |            显示已安装软件包的目录信息             |

使用`dpkg -L`查看`deb`包目录信息

```shell
sudo dpkg -L emacs24
```

## 19.进程管理

### top工具

top工具是常用的一个查看工具，能实时的查看我们系统的一些关键信息的变化:

```shell
top
```

top显示的第一行:

|             内容             |                  解释                   |
| :--------------------------: | :-------------------------------------: |
|             top              |           表示当前程序的名称            |
|           11:05:18           |          表示当前的系统的时间           |
|       up 8 days,17:12        |      表示该机器已经启动了多长时间       |
|            1 user            |       表示当前系统中只有一个用户        |
| load average: 0.29,0.20,0.25 | 分别对应 1、5、15 分钟内 cpu 的平均负载 |

top显示的第二行数据，基本上第二行是进程的一个情况统计:

|       内容       |         解释         |
| :--------------: | :------------------: |
| `Tasks: 26total` |       进程总数       |
|    1 running     | 1 个正在运行的进程数 |
|   25 sleeping    |  25 个睡眠的进程数   |
|    0 stopped     |   没有停止的进程数   |
|     0 zombie     |    没有僵尸进程数    |

top的第三行数据，这一行基本上是CPU的一个使用情况的统计

|       内容       |                                                         解释 |
| :--------------: | -----------------------------------------------------------: |
| `Cpu(s): 1.0%us` |                                  用户空间进程占用 CPU 百分比 |
|    `1.0% sy`     |                                  内核空间运行占用 CPU 百分比 |
|     `0.0%ni`     |              用户进程空间内改变过优先级的进程占用 CPU 百分比 |
|    `97.9%id`     |                                              空闲 CPU 百分比 |
|     `0.0%wa`     |                                等待输入输出的 CPU 时间百分比 |
|     `0.1%hi`     |                        硬中断(Hardware IRQ)占用 CPU 的百分比 |
|     `0.0%si`     |                        软中断(Software IRQ)占用 CPU 的百分比 |
|     `0.0%st`     | (Steal time) 是 hypervisor 等虚拟服务中，虚拟 CPU 等待实际 CPU 的时间的百分比 |

 top 的第四行数据，这一行基本上是内存的一个使用情况的统计：

|      内容      |         解释         |
| :------------: | :------------------: |
| 8176740 total  |     物理内存总量     |
|  8032104 used  |  使用的物理内存总量  |
|  144636 free   |     空闲内存总量     |
| 313088 buffers | 用作内核缓存的内存量 |

top 的第五行数据，这一行基本上是交换区的一个使用情况的统计：

|  内容  |                             解释                             |
| :----: | :----------------------------------------------------------: |
| total  |                          交换区总量                          |
|  used  |                       使用的交换区总量                       |
|  free  |                        空闲交换区总量                        |
| cached | 缓冲的交换区总量，内存中的内容被换出到交换区，而后又被换入到内存，但使用过的交换区尚未被覆盖 |

再下面就是进程的一个情况了

|  列名   |                     解释                     |
| :-----: | :------------------------------------------: |
|   PID   |                   进程 id                    |
|  USER   |               该进程的所属用户               |
|   PR    |        该进程执行的优先级 priority 值        |
|   NI    |               该进程的 nice 值               |
|  VIRT   |       该进程任务所使用的虚拟内存的总数       |
|   RES   | 该进程所使用的物理内存数，也称之为驻留内存数 |
|   SHR   |             该进程共享内存的大小             |
|    S    | 该进程进程的状态: S=sleep R=running Z=zombie |
|  %CPU   |             该进程 CPU 的利用率              |
|  %MEM   |              该进程内存的利用率              |
|  TIME+  |              该进程活跃的总时间              |
| COMMAND |               该进程运行的名字               |

top是一个前台程序，所以它是可以交互的:

| 常用交互命令 |                             解释                             |
| :----------- | :----------------------------------------------------------: |
| q            |                           退出程序                           |
| I            |               切换显示平均负载和启动时间的信息               |
| P            |               根据 CPU 使用百分比大小进行排序                |
| M            |                   根据驻留内存大小进行排序                   |
| i            |           忽略闲置和僵死的进程，这是一个开关式命令           |
| k            | 终止一个进程，系统提示输入 PID 及发送的信号值。一般终止进程用 15 信号，不能正常结束则使用 9 信号。安全模式下该命令被屏蔽。 |

### ps工具

ps也是常用的查看进程的工具

```shell
ps aux
```

```shell
ps axjf
```

|    内容     |                             解释                             |
| :---------: | :----------------------------------------------------------: |
|     `F`     | 进程的标志（process flags），当 flags 值为 1 则表示此子程序只是 fork 但没有执行 exec，为 4 表示此程序使用超级管理员 root 权限 |
|   `USER`    |                        进程的拥有用户                        |
|    `PID`    |                          进程的 ID                           |
|   `PPID`    |                        其父进程的 PID                        |
|    `SID`    |                        session 的 ID                         |
|   `TPGID`   |                       前台进程组的 ID                        |
|   `%CPU`    |                    进程占用的 CPU 百分比                     |
|   `%MEM`    |                       占用内存的百分比                       |
|    `NI`     |                        进程的 NICE 值                        |
|    `VSZ`    |                     进程使用虚拟内存大小                     |
|    `RSS`    |                      驻留内存中页的大小                      |
|    `TTY`    |                           终端 ID                            |
| `S or STAT` |                           进程状态                           |
|   `WCHAN`   |                      正在等待的进程资源                      |
|   `START`   |                        启动进程的时间                        |
|   `TIME`    |                     进程消耗 CPU 的时间                      |
|  `COMMAND`  |                       命令的名称和参数                       |

> **TPGID**栏写着-1 的都是没有控制终端的进程，也就是守护进程

> **STAT**表示进程的状态，而进程的状态有很多，如下表所示

| 状态 |                解释                |
| :--: | :--------------------------------: |
| `R`  |           Running.运行中           |
| `S`  |    Interruptible Sleep.等待调用    |
| `D`  | Uninterruptible Sleep.不可中断睡眠 |
| `T`  |      Stoped.暂停或者跟踪状态       |
| `X`  |          Dead.即将被撤销           |
| `Z`  |          Zombie.僵尸进程           |
| `W`  |          Paging.内存交换           |
| `N`  |           优先级低的进程           |
| `<`  |           优先级高的进程           |
| `s`  |            进程的领导者            |
| `L`  |              锁定状态              |
| `l`  |             多线程状态             |
| `+`  |              前台进程              |

使用-l参数可以显示这次登录的bash相关的进程信息罗列出来:

```shell
ps -l
```

罗列出所有的进程信息:

```shell
ps aux
```

查看时，将连同部分的进程呈树状显示出来:

```shell
ps axjf
```

### pstree工具

通过`pstree`可以很直接的看到相同的进程数量，还可以看到所有进程之间的相关性:

```
pstree -up
```

| 参数选择 |                解释                 |
| :------: | :---------------------------------: |
|    -A    |     程序树之间以 ASCII 字符连接     |
|    -p    |     同时列出每个 process 的 PID     |
|    -u    | 同时列出每个 process 的所属账户名称 |

### kill命令

当一个进程结束的时候或者要异常结束的时候，会向其父进程返回一个或者接收一个 SIGHUP 信号而做出的结束进程或者其他的操作，这个 SIGHUP 信号不仅可以由系统发送，我们可以使用 `kill` 来发送这个信号来操作进程的结束或者重启等等。

```shell
# 首先我们使用图形界面打开了 gedit、gvim，用 ps 可以查看到
ps aux

# 使用 9 这个信号强制结束 gedit 进程
kill -9 1608

# 我们再查找这个进程的时候就找不到了
ps aux | grep gedit
```

## 20.Linux日志系统

常见的日志一般都存放在`/var/log`中

我们可以根据服务对象粗略的将日志分为两类:**系统日志、应用日志**

常见的系统日志以及它们记录的信息:

|      日志名称      |                           记录信息                           |
| :----------------: | :----------------------------------------------------------: |
|  alternatives.log  |                  系统的一些更新替代信息记录                  |
|     apport.log     |                     应用程序崩溃信息记录                     |
|  apt/history.log   |             使用 apt-get 安装卸载软件的信息记录              |
|    apt/term.log    |     使用 apt-get 时的具体操作，如 package 的下载、打开等     |
|      auth.log      |                      登录认证的信息记录                      |
|      boot.log      |                系统启动时的程序服务的日志信息                |
|        btmp        |                        错误的信息记录                        |
| Consolekit/history |                       控制台的信息记录                       |
|    dist-upgrade    |             dist-upgrade 这种更新方式的信息记录              |
|       dmesg        |       启动时，显示屏幕上内核缓冲信息，与硬件有关的信息       |
|      dpkg.log      |                   dpkg 命令管理包的日志。                    |
|      faillog       |                   用户登录失败详细信息记录                   |
|   fontconfig.log   |                   与字体配置有关的信息记录                   |
|      kern.log      |        内核产生的信息记录，在自己修改内核时有很大帮助        |
|      lastlog       |                      用户的最近信息记录                      |
|        wtmp        | 登录信息的记录。wtmp 可以找出谁正在进入系统，谁使用命令显示这个文件或信息等 |
|       syslog       |                         系统信息记录                         |

我们还可以使用以下命令来看看`auth.log`中的信息:

```
sudo less /var/log/auth.log
```

## 附件:(1)Vi(Vim)键盘图

![image-20220820111411937](Typora\typora-user-images\image-20220820111411937.png)

## 附件:(2)Linux命令参考

![image-20220820112244994](Typora\typora-user-images\image-20220820112244994.png)

![image-20220820112251021](Typora\typora-user-images\image-20220820112251021.png)

## 附件:(3)正则表达式

正则表达式（Regular Expression）是一个定义搜索模式的字符序列。

简单而言，正则表达式通过使用一些特殊符号，使得使用者可以方便轻松地实现查找、删除、替换等功能。

### 正则表达式的特殊符号:

```reStructuredText
[:alnum:]代表英文大小写字母及数字
[:alpha:]代表英文大小写字母
[:blank:]代表空格和 tab 键
[:cntrl:]键盘上的控制按键，如 CR,LF,TAB,DEL
[:digit:]代表数字
[:graph:]代表空白字符以外的其他
[:lower:]小写字母
[:print:]可以被打印出来的任何字符
[:punct:]代表标点符号
[:upper:]代表大写字母
[:space:]任何会产生空白的字符如空格，tab,CR 等
[:xdigit:]代表 16 进位的数字类型
```

### 使用特殊符号查找小写字母:

```shell
grep -n '[[:lower:]]' regular_express.txt
```

### 使用特殊字符查找数字:

```shell
grep -n '[[:digit:]]' regular_express.txt
```

### grep命令

#### 查找特定字符串参数说明:

- -a ：以 text 档案的方式搜寻 binary 档案数据
- -c ：计算找到 '搜寻字符串' 的次数
- -i ：忽略大小写的不同，所以大小写视为相同
- -n ：顺便输出行号
- -v ：反向选择，亦即显示没有 '搜寻字符串' 内容的行

### 字符组匹配

`[]`可以用来查找字符组。无论 `[ ]` 中包含多少个字符，它都只代表一个字符。

```shell
grep -n 't[ae]st' regular_express.txt
```

字符组支持使用连字符 `-` 来表示一个范围。当 `-` 前后构成范围时，要求前面字符的码位小于后面字符的码位。

`[^]` 为反向选择字符组，用于排除后面的字符，使用方式为 `[^...]`。

**注意**:`[^]` 与参数 `-v` 的区别，尽管二者都表示反向选择，但是如果包含有反向选择的字符的行含有其他字符的话，`[^]` 仍会输出该行，而 `-v` 则只会输出不含有反向选择的字符的行。

### 行首符^与行尾符$

查找行首为大写字母的所有行:

```shell
grep -n '^[A-Z]' regular_express.txt
```

**注意**:行首符 `^` 和反向选择 `[^]` 的区别，`^[A-Z]` 表示以大写字母开头。`[^A-Z]` 表示除了大写字母 A-Z 的所有字符。

行尾符$的用法与行首符类似

查找以字母d结尾的行:

```shell
grep -n 'd$' regular_express.txt
```

将行首符与行尾符连用,可以用来查找空行:

```shell
grep -n '^$' regular_express.txt
```

应用实例:

查看`/etcinsserv.conf`文档

`^$`:过滤掉空白行

`^#`:过滤掉注释行(以#号开头)

```shell
cat -n /etc/insserv.conf
grep -v '^$' /etc/insserv.conf | grep -v '^#'
```

### 任意一个字符.与重复字符*

查找`a?ou?`类型的字符

```shell
grep -n 'a.ou.' regular_express.txt
```

其中小数点表示任意一个字符，一个小数点只能表示一个未知字符。

```shell
*（星号）：代表重复前面 0 个或者多个字符。
e*： 表示具有空字符或者一个以上 e 字符。
ee*，表示前面的第一个 e 字符必须存在。第二个 e 则可以是 0 个或者多个 e 字符。
eee*，表示前面两个 e 字符必须存在。第三个 e 则可以是 0 个或者多个 e 字符。
ee*e ：表示前面的第一个与第三个 e 字符必须存在。第二个 e 则可以是 0 个或者多个 e 字符。
```

`{}`可限制一个范围区间内的重复字符数。由于`{`与`}`在shell中有特殊意义，故在使用时需要用到转义字符`\`。

查找连续的两个o字符:

```shell
grep -n 'o\{2\}' regular_express.txt
```

查找g后面接2到5个o，然后再接g的字符串:

```shell
grep -n 'go\{2,5\}g' regular_express.txt
```

总结:

```shell
^word    表示待搜寻的字符串(word)在行首
word$    表示待搜寻的字符串(word)在行尾
.(小数点) 表示 1 个任意字符
\        表示转义字符，在特殊字符前加 \ 会将特殊字符意义去除
*        表示重复 0 到无穷多个前一个 RE(正则表达式)字符
[list]   表示搜索含有 l,i,s,t 任意字符的字符串
[n1-n2]  表示搜索指定的字符串范围,例如 [0-9] [a-z] [A-Z] 等
[^list]  表示反向字符串的范围,例如 [^0-9] 表示非数字字符，[^A-Z] 表示非大写字符范围
\{n,m\}  表示找出 n 到 m 个前一个 RE 字符
\{n,\}   表示 n 个以上的前一个 RE 字符
```

### sed命令

#### 输出文件内容

将regular_express.txt的内容列出并打印行号,并将2-5行删除显示:

```shell
nl regular_express.txt | sed '2,5d'
```

`2,5d` 表示删除 2~5 行，d 即为 delete。

删除第2行:

```shell
nl regular_express.txt | sed '2d'
```

删除第三行到最后一行,`$`表示定位到最后一行:

```shell
nl regular_express.txt | sed '3,$d'
```

使用`-i`在原文件中删除第1行:

```shell
sed -i '1d' regular_express.txt
```

#### 使用a和i新增输出

在第二行后添加字符串test:

```shell
nl regular_express.txt | sed '2a test'
```

在第二行前添添加字符串test:

```shell
nl regular_express.txt | sed '2i test'
```

在第二行后添加两行test，`\n`表示换行符:

```shell
nl regular_express.txt | sed '2a test\ntest'
```

#### 行内容体寒

将2-5行的内容替换为No 2-5 number,c为替换内容选项:

```shell
nl regular_express.txt | sed '2,5c No 2-5 number'
```

#### 输出指定行

输出regular_express.txt的第5-7行，其中`-n`为安静模式选项。

```shell
nl regular_express.txt | sed -n '5,7p'
nl regular_express.txt | sed '5,7p'
```

#### 使用扩展正则表达式egrep

使用扩展正则表达式来去除空白行和注释行的指令:

```shell
egrep -v '^$|^#' regular_express.txt
```

利用支持扩展正则表达式的 `egrep` 与特殊字符 `|` 的组合功能来间隔两组字符串，

#### 扩展规则

`+`表示重复一个或一个以上的前一个字符

```
egrep -n 'go+d' regular_express.txt
```

`?`表示重复零个或一个的前一个字符

```shell
egrep -n 'go?d' regular_express.txt
```

`|`表示用或(or)的方式找出数个字符串

查找gd或good:

```shell
egrep -n 'gd|good' regular_express.txt
```

`()`表示找出组字符串

查找glad或good，由于二者存在重复字母，所以可以将其合并

```shell
egrep -n 'g(la|oo)d' regular_express.txt
```

`()+`多个重复群组判别

查找开头是A结尾是C中间有一个以上的`xyz`或`xz`字符串:

```shell
echo 'AxyzxyzxyzxyzC' | egrep 'A(xyz)+C'
echo 'AxyzxyzxyzxyzC' | egrep 'A(xz)+C'
```

