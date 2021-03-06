﻿using ProjectManagementTool.classes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace ProjectManagementTool
{
    /// <summary>
    /// Interaction logic for RegisterWindow.xaml
    /// </summary>
    public partial class RegisterWindow : Window
    {
        public RegisterWindow()
        {
            InitializeComponent();
        }

        //This method just goes back to the login screen
        private void BtnCancel_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        //TODO Register user and check on textboxes [check if user already exists]
        private void BtnRegister_Click(object sender, RoutedEventArgs e)
        {
            Kontext ktx = Kontext.GetInstance();
            if (txtPassword.Password.Equals(txtConfirmPassword.Password))
            {
                if (txtPassword.Password.Equals("") || txtConfirmPassword.Password.Equals("")||txtUsername.Text.Equals("")||
                    txtEMail.Text.Equals(""))
                {
                    MessageBox.Show("A field can not be left empty");
                }
                else
                {
                    if (IsValidEmail(txtEMail.Text))
                    {
                        ktx.AddUser(new User(txtUsername.Text, txtPassword.Password, txtEMail.Text));
                        MessageBox.Show("Succesfully registered!");
                        this.Close();
                    }
                }
            }
            else
            {
                MessageBox.Show("Password not identical.");
            }
        }

        //Check the format of the email adress
        private bool IsValidEmail(string email)
        {
            bool retVal = false;
            try
            {
                var addr = new System.Net.Mail.MailAddress(email);
                retVal = (addr.Address == email);
            }
            catch
            {
                MessageBox.Show("E-Mail Adress is not valid!");
            }
            return retVal;
        }
    }
}
