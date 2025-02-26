import { Builder, By, until, Select } from 'selenium-webdriver';
async function A1_scenerio() {
    let driver = await new Builder().forBrowser('chrome').build();
    try {
        console.log("A1_scenerio")
        await driver.get('http://localhost:5173');
        await driver.manage().window().maximize();

        let startButton = await driver.findElement(By.xpath("//button[contains(text(), 'Start Game')]"));
        let selectScenerio = await driver.findElement(By.id("scenerios"));
        let select = new Select(selectScenerio);
        await select.selectByVisibleText('A1_scenario');
        await startButton.click();
        await driver.sleep(2000)
        let inputBar = await driver.findElement(By.id('input-bar'));
        let inputButton = await driver.findElement(By.id('enter-input-btn'));

        //P1 Drawing Q4 and P2 Accepting Sponsorship
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Draw")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"No")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P2 Building Quest and All Players Participating in Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"7")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"2")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"5")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"2")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"3")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"5")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"2")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"3")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await inputButton.click()

        //P2 Building Quest and All Players Participating in Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P1 Trimming and Building Attack for Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"5")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"5")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P3 Trimming and Building Attack for Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"5")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"4")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P4 Trimming and Building Attack for Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"4")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P1,P3,P4 Accepting Stage 2
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P1 Building Attack for Stage 2
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"7")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P3 Building Attack for Stage 2
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"9")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()


        //P4 Building Attack for Stage 2
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P3,P4 Accepting Stage 3
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P3 Building Attack for Stage 3
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"10")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"7")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"5")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P4 Building Attack for Stage 3
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"7")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"7")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P3,P4 Accepting Stage 4
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P3 Building Attack for Stage 3
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"7")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P4 Building Attack for Stage 3
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"4")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"4")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"4")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"5")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        await inputButton.click()
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await driver.sleep(5000)

        //-----------------ASSERTS----------------------------
        await assertPlayerSheild(driver,1,0)
        await assertPlayerSheild(driver,2,0)
        await assertPlayerSheild(driver,3,0)
        await assertPlayerSheild(driver,4,4)

        let finalPlayerHands = [
            ["F5","F10","F15","F15","F30","H","B","B","L"],
            ["F5","F5","F15","F30","S"],
            ["F15","F15","F40","L"],
        ];

        await assertPlayerHandLength(driver,1,finalPlayerHands[0].length)
        await assertPlayerHandLength(driver,2,12)
        await assertPlayerHandLength(driver,3,finalPlayerHands[1].length)
        await assertPlayerHandLength(driver,4,finalPlayerHands[2].length)
    
        await assertPlayerHand(driver,1,finalPlayerHands[0])
        await assertPlayerHand(driver,3,finalPlayerHands[1])
        await assertPlayerHand(driver,4,finalPlayerHands[2])
        console.log("\n")


    } catch (error) {
        console.error("Test encountered an error:", error);
    } finally {
        await driver.quit();
    }
}

async function two_winner_game_two_winner_quest() {
    let driver = await new Builder().forBrowser('chrome').build();
    try {
        console.log("2_winner_game_2_winner_quest")
        await driver.get('http://localhost:5173');
        await driver.manage().window().maximize();

        let startButton = await driver.findElement(By.xpath("//button[contains(text(), 'Start Game')]"));
        let selectScenerio = await driver.findElement(By.id("scenerios"));
        let select = new Select(selectScenerio);
        await select.selectByVisibleText('2_winner');
        await startButton.click();
        await driver.sleep(2000)
        let inputBar = await driver.findElement(By.id('input-bar'));
        let inputButton = await driver.findElement(By.id('enter-input-btn'));

        //P1 Drawing Q4 and P2 Accepting Sponsorship
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Draw")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P2 Building Quest and All Players Participating in Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"5")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"4")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"4")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await inputButton.click()

        //P2 Building Quest and All Players Participating in Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P2 Trimming and Building Attack for Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P3 Trimming and Building Attack for Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P4 Trimming and Building Attack for Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P2,P4 Accepting Stage 2
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P2 Building Attack for Stage 2
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"4")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P4 Building Attack for Stage 2
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"4")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P2,P4 Accepting Stage 3
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P2 Building Attack for Stage 3
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"7")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P4 Building Attack for Stage 3
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"7")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P3,P4 Accepting Stage 4
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P2 Building Attack for Stage 4
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P4 Building Attack for Stage 4
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P1 Redraw
        await inputButton.click()
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await driver.sleep(100)
        //await inputButton.click()


        await typeAndSubmitAnswer(driver,inputBar,inputButton,"End")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Draw")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"No")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P3 Building Quest and All Players Participating in Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"3")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"4")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await inputButton.click()

        //P2 Building Quest and All Players Participating in Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"No")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P2 Building Attack for Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P4 Building Attack for Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P2 Building Attack for Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"7")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P4 Building Attack for Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"7")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P2 Building Attack for Stage 3
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"10")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P4 Building Attack for Stage 3
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"10")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P3 Redraw
        await inputButton.click()
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"2")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"2")
        await driver.sleep(5000)

        //-----------------ASSERTS----------------------------
        
        await assertPlayerSheild(driver,1,0)
        await assertPlayerSheild(driver,2,7)
        await assertPlayerSheild(driver,3,0)
        await assertPlayerSheild(driver,4,7)

        let finalPlayerHands = [
            ["F15","F15","F20","F20","F20","F20","F25","F25","F30","H","B","L"],
            ["F10","F15","F15","F25","F30","F40","F50","L","L"],
            ["F20","F40","D","D","S","H","H","H","H","B","B","L"],
            ["F15","F15","F20","F25","F30","F50","F70","L","L"]
        ];

        await assertPlayerHandLength(driver,1,finalPlayerHands[0].length)
        await assertPlayerHandLength(driver,2,finalPlayerHands[1].length)
        await assertPlayerHandLength(driver,3,finalPlayerHands[2].length)
        await assertPlayerHandLength(driver,4,finalPlayerHands[3].length)
    
        await assertPlayerHand(driver,1,finalPlayerHands[0])
        await assertPlayerHand(driver,2,finalPlayerHands[1])
        await assertPlayerHand(driver,3,finalPlayerHands[2])
        await assertPlayerHand(driver,4,finalPlayerHands[3])
        
        await assertPlayerIsWinner(driver,2)
        await assertPlayerIsWinner(driver,4)

        console.log("\n")


    } catch (error) {
        console.error("Test encountered an error:", error);
    } finally {
        await driver.quit();
    }
}

async function one_winner_game_with_events() {
    let driver = await new Builder().forBrowser('chrome').build();
    try {
        console.log("1_winner_game_with_events")
        await driver.get('http://localhost:5173');
        await driver.manage().window().maximize();

        let startButton = await driver.findElement(By.xpath("//button[contains(text(), 'Start Game')]"));
        let selectScenerio = await driver.findElement(By.id("scenerios"));
        let select = new Select(selectScenerio);
        await select.selectByVisibleText('1_winner');
        await startButton.click();
        await driver.sleep(2000)
        let inputBar = await driver.findElement(By.id('input-bar'));
        let inputButton = await driver.findElement(By.id('enter-input-btn'));


        //P1 Drawing Q4 and P2 Accepting Sponsorship
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Draw")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P2 Building Quest and All Players Participating in Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"2")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"3")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"4")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await inputButton.click()

        //P2 Building Quest and All Players Participating in Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P2 Trimming and Building Attack for Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"3")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P3 Trimming and Building Attack for Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"3")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P4 Trimming and Building Attack for Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"4")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P2,P3,P4 Accepting Stage 2
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")


        //P2 Building Attack for Stage 2
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P3 Building Attack for Stage 2
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P4 Building Attack for Stage 2
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"7")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P2,P3,P4 Accepting Stage 3
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")


        //P2 Building Attack for Stage 3
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"8")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P3 Building Attack for Stage 3
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"8")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P4 Building Attack for Stage 3
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"9")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P2,P3,P4 Accepting Stage 4
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")


        //P2 Building Attack for Stage 4
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"10")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P3 Building Attack for Stage 4
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"10")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P4 Building Attack for Stage 4
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"11")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P1 Redraw
        await inputButton.click()
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"2")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"2")
        await driver.sleep(100)
        //await inputButton.click()


        await typeAndSubmitAnswer(driver,inputBar,inputButton,"End")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Draw")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"End")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Draw")

        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"End")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Draw")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"2")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"4")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"End")

        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Draw")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P1 Building Quest
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"7")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"4")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P2 Building Attack for Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"9")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P3 Building Attack for Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"9")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P4 Building Attack for Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"10")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P2 Building Attack for Stage 2
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"10")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"8")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P3 Building Attack for Stage 2
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"10")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"5")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P2 Building Attack for Stage 3
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"10")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"5")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P3 Building Attack for Stage 3
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"11")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        await inputButton.click()
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await driver.sleep(5000)

        //-----------------ASSERTS----------------------------
        
        await assertPlayerSheild(driver,1,0)
        await assertPlayerSheild(driver,2,5)
        await assertPlayerSheild(driver,3,7)
        await assertPlayerSheild(driver,4,4)

        let finalPlayerHands = [
            ["F25","F25","F35","D","D","S","S","S","S","H","H","H"],
            ["F15","F25","F30","F40","S","S","S","H","E"],
            ["F10","F25","F30","F40","F50","S","S","H","H","L"],
            ["F25","F25","F30","F50","F70","D","D","S","S","B","L"]
        ];

        await assertPlayerHandLength(driver,1,finalPlayerHands[0].length)
        await assertPlayerHandLength(driver,2,finalPlayerHands[1].length)
        await assertPlayerHandLength(driver,3,finalPlayerHands[2].length)
        await assertPlayerHandLength(driver,4,finalPlayerHands[3].length)
    
        await assertPlayerHand(driver,1,finalPlayerHands[0])
        await assertPlayerHand(driver,2,finalPlayerHands[1])
        await assertPlayerHand(driver,3,finalPlayerHands[2])
        await assertPlayerHand(driver,4,finalPlayerHands[3])
        
        await assertPlayerIsWinner(driver,3)
        console.log("\n")


    } catch (error) {
        console.error("Test encountered an error:", error);
    } finally {
        await driver.quit();
    }
}

async function zero_winner_quest() {
    let driver = await new Builder().forBrowser('chrome').build();
    try {
        console.log("0_winner_quest")
        await driver.get('http://localhost:5173');
        await driver.manage().window().maximize();

        let startButton = await driver.findElement(By.xpath("//button[contains(text(), 'Start Game')]"));
        let selectScenerio = await driver.findElement(By.id("scenerios"));
        let select = new Select(selectScenerio);
        await select.selectByVisibleText('0_winner');
        await startButton.click();
        await driver.sleep(2000)
        let inputBar = await driver.findElement(By.id('input-bar'));
        let inputButton = await driver.findElement(By.id('enter-input-btn'));

        //P1 Drawing Q4 and P2 Accepting Sponsorship
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Draw")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P2 Building Quest and All Players Participating in Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"2")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"3")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"4")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"5")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"6")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await inputButton.click()

        //P2 Building Quest and All Players Participating in Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Yes")

        //P2 Trimming and Building Attack for Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"12")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P3 Trimming and Building Attack for Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"4")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()

        //P4 Trimming and Building Attack for Stage 1
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"3")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"Quit")
        await driver.sleep(100)
        await inputButton.click()


        await inputButton.click()
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await typeAndSubmitAnswer(driver,inputBar,inputButton,"1")
        await driver.sleep(5000)

        //-----------------ASSERTS----------------------------
        await assertPlayerSheild(driver,1,0)
        await assertPlayerSheild(driver,2,0)
        await assertPlayerSheild(driver,3,0)
        await assertPlayerSheild(driver,4,0)

        let finalPlayerHands = [
            ["F15","D","D","D","D","S","S","S","H","H","H","H"],
            ["F5","F5","F10","F15","F15","F20","F20","F25","F30","F30","F40"],
            ["F5","F5","F10","F15","F15","F20","F20","F25","F25","F30","F40","L"],
            ["F5","F5","F10","F15","F15","F20","F20","F25","F25","F30","F50","E"]
        ];

        await assertPlayerHandLength(driver,1,finalPlayerHands[0].length)
        await assertPlayerHandLength(driver,2,finalPlayerHands[1].length)
        await assertPlayerHandLength(driver,3,finalPlayerHands[2].length)
        await assertPlayerHandLength(driver,4,finalPlayerHands[3].length)
    
        await assertPlayerHand(driver,1,finalPlayerHands[0])
        await assertPlayerHand(driver,2,finalPlayerHands[1])
        await assertPlayerHand(driver,3,finalPlayerHands[2])
        await assertPlayerHand(driver,4,finalPlayerHands[3])

    } catch (error) {
        console.error("Test encountered an error:", error);
    } finally {
        await driver.quit();
    }
}

async function typeAndSubmitAnswer(driver,inputBar,submitButton,answer) {
    await driver.sleep(100)
    await inputBar.sendKeys(answer)
    await driver.sleep(100)
    await submitButton.click()
}


async function assertPlayerSheild(driver, playerId, expectedShield) {
    try {
        let playerShieldElement = await driver.findElement(By.id(`p${playerId}-shields`));
        let actualShieldCount = parseInt(await playerShieldElement.getText());

        console.assert(
            actualShieldCount === expectedShield,
            `P${playerId} shields mismatch! Expected: ${expectedShield}, Found: ${actualShieldCount}`
        );

        if (actualShieldCount === expectedShield) {
            console.log(`✅ P${playerId} shield count is correct: ${actualShieldCount}`);
        }
    } catch (error) {
        console.error("Error in assertPlayerShield:", error);
    }
}

async function assertPlayerHandLength(driver, playerId, expectedCount) {
    try {
        let cards = await driver.findElements(By.css(`#p${playerId}-grid .card`));
        let actualCardCount = cards.length;

        console.assert(
            actualCardCount === expectedCount,
            `P${playerId} hand size mismatch! Expected: ${expectedCount}, Found: ${actualCardCount}`
        );

        if (actualCardCount === expectedCount) {
            console.log(`✅ P${playerId} hand size is correct: ${actualCardCount}`);
        }
    } catch (error) {
        console.error("Error in assertPlayerHandLength:", error);
    }
}

async function assertPlayerHand(driver, playerId, expectedHand) {
    try {
        let cards = await driver.findElements(By.css(`#p${playerId}-grid .card`));
        let actualHand = [];
        for (let cardElement of cards) {
            let cardText = await cardElement.getText();
            actualHand.push(cardText.trim());
        }
        let cleanedCards = actualHand.map(card => card.replace(/^\d+\.\s*/, ''));

        console.assert(
            JSON.stringify(cleanedCards) === JSON.stringify(expectedHand),
            `P${playerId} hand mismatch! Expected: ${JSON.stringify(expectedHand)}, Found: ${JSON.stringify(cleanedCards)}`
        );

        if (JSON.stringify(cleanedCards) === JSON.stringify(expectedHand)) {
            console.log(`✅ P${playerId} hand is correct: ${JSON.stringify(cleanedCards)}`);
        }
    } catch (error) {
        console.error("Error in assertPlayerHand:", error);
    }
}

async function assertPlayerIsWinner(driver, playerId) {
    try {
        let winnerHeader = await driver.findElement(By.id(`current-player-header`));
        let winnerText = await winnerHeader.getText();
        let expectedWinnerSubstring = `P${playerId}`;

        console.assert(
            winnerText.includes(expectedWinnerSubstring),
            `P${playerId} winner mismatch! Expected text to contain: "${expectedWinnerSubstring}", Found: "${winnerText}"`
        );

        if (winnerText.includes(expectedWinnerSubstring)) {
            console.log(`✅ P${playerId} is correctly displayed as a winner`);
        }
    } catch (error) {
        console.error("Error in assertPlayerIsWinner:", error);
    }
}

await A1_scenerio();
await two_winner_game_two_winner_quest();
await one_winner_game_with_events();
await zero_winner_quest();