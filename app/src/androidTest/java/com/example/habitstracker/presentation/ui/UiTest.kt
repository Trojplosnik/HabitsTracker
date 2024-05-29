package com.example.habitstracker.presentation.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.habitstracker.presentation.MainActivity
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.kakao.screen.Screen.Companion.onScreen
import org.junit.Rule
import org.junit.Test

class UiTest : TestCase() {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun createHabitTest() {
        run {

            step("Open EditAdd screen") {
                onScreen<HabitsScreen> {
                    fab {
                        isVisible()
                        isClickable()
                        click()
                    }
                }

            }
            step("Fulfill habit fields")
            {
                onScreen<EditAddScreen> {
                    name.replaceText("TestName")
                    description.replaceText("Test description")
                    amount.replaceText("3")
                    goodRadio.click()
                    priority.isVisible()
                    frequency.isVisible()
                }
            }
            step("Save habit and back to habits screen")
            {
                onScreen<EditAddScreen> {
                    close {
                        isVisible()
                        isClickable()
                    }
                    save {
                        isVisible()
                        isClickable()
                        click()
                    }
                }
            }

            step("Open EditAdd screen second time") {
                onScreen<HabitsScreen> {
                    fab {
                        isVisible()
                        isClickable()
                        click()
                    }
                }
            }

            step("Filling not all habit fields")
            {
                onScreen<EditAddScreen> {
                    description.replaceText("Test description 2")
                    amount.replaceText("1")
                    badRadio.click()
                    priority.isVisible()
                    frequency.isVisible()
                }
            }

            step("Try to  go back to habits screen")
            {
                onScreen<EditAddScreen> {
                    save {
                        isVisible()
                        isClickable()
                        click()
                    }
                }
            }

            step("Filling all habit fields")
            {
                onScreen<EditAddScreen> {
                    name.replaceText("TestName2")
                    description.replaceText("Test description 3")
                }

            }

            step("Back to habits screen")
            {
                onScreen<EditAddScreen> {
                    close {
                        isVisible()
                        isClickable()
                        click()
                    }
                }
            }

        }
    }
}