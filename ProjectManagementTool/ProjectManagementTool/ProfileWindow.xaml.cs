using Microsoft.Win32;
using ProjectManagementTool.classes;
using System;
using System.Collections.Generic;
using System.IO;
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
using System.Drawing;

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

        Kontext k = Kontext.GetInstance();
        

        private void LoadGUI()
        {
            txtName.Visibility = Visibility.Hidden;
            txtPassword.Visibility = Visibility.Hidden;
            txtEmail.Visibility = Visibility.Hidden;
            btnSave.Visibility = Visibility.Hidden;
            lblName.Content = k.GetCurrentUser().Username;
            lblPassword.Content = k.GetCurrentUser().Password;
            lblEmail.Content = k.GetCurrentUser().Email;
            if(k.GetCurrentUser().Picture != null)
            {
                byte[] blob = k.GetCurrentUser().Picture;
                MemoryStream stream = new MemoryStream();
                stream.Write(blob, 0, blob.Length);
                stream.Position = 0;

                System.Drawing.Image img = System.Drawing.Image.FromStream(stream);
                BitmapImage bi = new BitmapImage();
                bi.BeginInit();

                MemoryStream ms = new MemoryStream();
                img.Save(ms, System.Drawing.Imaging.ImageFormat.Bmp);
                ms.Seek(0, SeekOrigin.Begin);
                bi.StreamSource = ms;
                bi.EndInit();
                imgProfilBild.Source = bi;
            }


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
            txtEmail.Text = k.GetCurrentUser().Email;
            txtName.Text = k.GetCurrentUser().Username;
            txtPassword.Text = k.GetCurrentUser().Password;
        }

        private void btnSave_Click(object sender, RoutedEventArgs e)
        {
            k.UpdateUser(new User(k.GetCurrentUser().Id, txtName.Text, txtPassword.Text, txtEmail.Text));
            lblName.Visibility = Visibility.Visible;
            lblPassword.Visibility = Visibility.Visible;
            lblEmail.Visibility = Visibility.Visible;
            btnProfileEdit.Visibility = Visibility.Visible;
            txtName.Visibility = Visibility.Hidden;
            txtPassword.Visibility = Visibility.Hidden;
            txtEmail.Visibility = Visibility.Hidden;
            btnSave.Visibility = Visibility.Hidden;
            lblName.Content = k.GetCurrentUser().Username;
            lblPassword.Content = k.GetCurrentUser().Password;
            lblEmail.Content = k.GetCurrentUser().Email;

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
                string source = imgProfilBild.Source.ToString().Substring(8);
                Console.WriteLine(source);
                FileStream fs = new FileStream(source, FileMode.Open, FileAccess.Read);

                // Create a byte array of file stream length
                byte[] ImageData = new byte[fs.Length];

                //Read block of bytes from stream into the byte array
                fs.Read(ImageData, 0, System.Convert.ToInt32(fs.Length));
                k.UpdateUser(new User(k.GetCurrentUser().Id, lblName.Content.ToString(), lblPassword.Content.ToString(), lblEmail.Content.ToString(), ImageData));
            }
            
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            Window w = Application.Current.Windows.OfType<Window>().SingleOrDefault(x => x.IsActive);
            w.Close();
            Main m = new Main(k.GetCurrentUser());
            m.Show();
        }
    }
}
