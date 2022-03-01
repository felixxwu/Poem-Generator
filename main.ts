import * as fs from 'fs'
import * as readline from 'readline'

const myPoem: string[] = []
const poems = fs.readFileSync('archive.txt','utf8')
const poemArray = poems.split('\n').filter(line => line.length > 1)
let continueWriting = true

async function main() {
    while (continueWriting) {
        console.log()

        if (myPoem.length !== 0) {
            console.log('My poem so far:')
            printPoem()
        }

        console.log('New line:')
        const newLine = getRandomLine()
        console.log(newLine)
        console.log()

        const ans = await getUserInput()
        if (ans === 'add') myPoem.push(newLine)
        if (ans === 'next') continue
        if (ans === 'finish') continueWriting = false
    }

    console.log('Completed poem:')
    printPoem()
    writePoem()
}

main()

function askUser(query: string) {
    const rl = readline.createInterface({
        input: process.stdin,
        output: process.stdout,
    });

    return new Promise<string>(resolve => rl.question(query + '\n', ans => {
        rl.close();
        resolve(ans);
    }))
}

type UserChoice = 'add' | 'next' | 'finish'

async function getUserInput(): Promise<UserChoice> {
    const ans = await askUser('Add line [a] - Next line [n] - Finish [f]')
    if (ans.toLowerCase() === 'a') {
        return 'add'
    } else if (ans.toLowerCase() === 'n') {
        return 'next'
    } else if (ans.toLowerCase() === 'f') {
        return 'finish'
    } else {
        return await getUserInput()
    }
}

function getRandomLine() {
    return poemArray[Math.floor(Math.random() * poemArray.length)]
}

function printPoem() {
    console.log()
    console.log('--------------------')
    myPoem.forEach(l => console.log(l))
    console.log('--------------------')
    console.log()
}

function writePoem() {
    if (myPoem.length === 0) return
    const path = `poems/${(new Date()).toDateString()}.txt`
    const content = myPoem.join('\n')
    fs.writeFileSync(path, content)
}
