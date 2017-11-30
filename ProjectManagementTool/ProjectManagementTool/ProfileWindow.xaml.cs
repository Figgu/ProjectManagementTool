using Microsoft.Win32;
using ProjectManagementTool.classes;
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
    /// Interaktionslogik für Profile.xaml
    /// </summary>
    public partial class ProfileWindow : Window
    {
        public ProfileWindow()
        {
            InitializeComponent();
            LoadGUI();
        }

        Kontext k = Kontext.getIntance();
        

        private void LoadGUI()
        {
            txtName.Visibility = Visibility.Hidden;
            txtPassword.Visibility = Visibility.Hidden;
            txtEmail.Visibility = Visibility.Hidden;
            btnSave.Visibility = Visibility.Hidden;
            lblName.Content = k.getCurrentUser().Username;
            lblPassword.Content = k.getCurrentUser().Password;
            lblEmail.Content = k.getCurrentUser().Email;
        }

        private void btnProfileEdit_Click(object sender, RoutedEventArgs e)
        {
            lblName.Visibility = Visibility.Hidden;
            lblPassword.Visibility = Visibility.Hidden;
            lblEmail.Visibility = Visibility.Hidden;
            btnProfileEdit.Visibility = Visibility.Hidden;
            txtName.Visibility = Visibility.Visible;
            txtPassword.Visibility = Visibility.Visible;
            txtEmail.Visibility = Visibility.Visible;
            btnSave.Visibility = Visibility.Visible;
            txtEmail.Text = k.getCurrentUser().Email;
            txtName.Text = k.getCurrentUser().Username;
            txtPassword.Text = k.getCurrentUser().Password;
        }

        private void btnSave_Click(object sender, RoutedEventArgs e)
        {
            k.updateUser(new User(k.getCurrentUser().Id, txtName.Text, txtPassword.Text, txtEmail.Text));
            lblName.Visibility = Visibility.Visible;
            lblPassword.Visibility = Visibility.Visible;
            lblEmail.Visibility = Visibility.Visible;
            btnProfileEdit.Visibility = Visibility.Visible;
            txtName.Visibility = Visibility.Hidden;
            txtPassword.Visibility = Visibility.Hidden;
            txtEmail.Visibility = Visibility.Hidden;
            btnSave.Visibility = Visibility.Hidden;
            lblName.Content = k.getCurrentUser().Username;
            lblPassword.Content = k.getCurrentUser().Password;
            lblEmail.Content = k.getCurrentUser().Email;
            
        }

        private void btnEditPicture_Click(object sender, RoutedEventArgs e)
        {
            OpenFileDialog op = new OpenFileDialog();
            op.Title = "Select a picture";
            op.Filter = "All supported graphics|*.jpg;*.jpeg;*.png|" +
              "JPEG (*.jpg;*.jpeg)|*.jpg;*.jpeg|" +
              "Portable Network Graphic (*.png)|*.png";
            if (op.ShowDialog() == true)
            {
                imgProfilBild.Source = new BitmapImage(new Uri(op.FileName));
            }
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            Window w = Application.Current.Windows.OfType<Window>().SingleOrDefault(x => x.IsActive);
            w.Close();
            Main m = new Main(k.getCurrentUser());
            m.Show();
        }
    }
}
