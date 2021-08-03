/***
 LockCLI - A CLI for locking the terminal while absent from your Computer.
 Copyright (C) 2021 shabman
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.shab.lockcli;

import java.util.Scanner;

public class Main implements Runnable {

    private boolean session = true;
    private boolean isLocked = false;

    private final String[] commands = {
            "lock",
            "unlock"
    };

    public static void main(String[] args) {
        new Main().run();
    }

    @Override
    public void run() {
        this.sessionThread();
    }

    public Object listen() {
        Scanner scanner = null;
        if (this.session) {
            scanner = new Scanner(System.in);
            return scanner.next();
        } else {
            if (scanner == null) { return null; }
            scanner.close();
            return null;
        }
    }

    public void sessionThread() {
        while (this.session) {
            Object input = this.listen();
            if (input == null) {
                return;
            }
            String value = String.valueOf(input);
            if (value.toLowerCase().equals(commands[0])) {
                this.output(">>> Terminal locked!");
                this.isLocked = true;
            } else if (value.toLowerCase().equals(commands[1])) {
                this.output(">>> Terminal Unlocked!");
                this.session = false;
                this.isLocked = false;
            } else if (this.isLocked) {
                this.output(">>> Cannot access command: " + value + " >>> The terminal is locked!");
            }
        }
    }

    public void output(String message) {
        System.out.println(message);
    }
}
