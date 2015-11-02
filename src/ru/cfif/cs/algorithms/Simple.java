package ru.cfif.cs.algorithms;

import java.util.*;
import java.util.function.*;


public class Simple {
	public static void main(String[] args) {


		// Random variables
		String randomFrom = "ASkjklasdlkjasdkljdas;lASd ;lasd kj;l"; // ��������� ��������� ������. ������ ������� �� ��������������.
		String randomTo = "sd;lfks;ldf';l ;ldsf ;l";  // ��������� ��������� ������. ������ ������� �� ��������������.
		int randomSalary = 100;  // ��������� ��������� ����� ������������� �����. ������ ������� ��� ��������������.

		// �������� ������ �� ���� �������� ���������.
		MailMessage firstMessage = new MailMessage(
			"Robert Howard",
			"H.P. Lovecraft",
			"This \"The Shadow over Innsmouth\" story is real masterpiece, Howard!"
		);

		assert firstMessage.getFrom().equals("Robert Howard") : "Wrong firstMessage from address";
		assert firstMessage.getTo().equals("H.P. Lovecraft") : "Wrong firstMessage to address";
		assert firstMessage.getContent().endsWith("Howard!") : "Wrong firstMessage content ending";

		MailMessage secondMessage = new MailMessage(
			"Jonathan Nolan",
			"Christopher Nolan",
			"����, ������ ��� ��� ������ ������ ����, ����� ����������� ��� �������� ������� �. ��� �� ������!"
		);

		MailMessage thirdMessage = new MailMessage(
			"Stephen Hawking",
			"Christopher Nolan",
			"� ��� � �� ����� ������������."
		);

		List<MailMessage> messages = Arrays.asList(
			firstMessage, secondMessage, thirdMessage
		);

		// �������� ��������� �������.
		MailService<String> mailService = new MailService<>();

		// ��������� ������ ����� �������� ��������
		messages.stream().forEachOrdered(mailService);

		// ��������� � �������� ������� "��������� �����",
//   ��� �� ���������� ����� �������� ������ ���������, ������� ���� ��� ����������
		Map<String, List<String>> mailBox = mailService.getMailBox();

		assert mailBox.get("H.P. Lovecraft").equals(
			Arrays.asList(
				"This \"The Shadow over Innsmouth\" story is real masterpiece, Howard!"
			)
		) : "wrong mailService mailbox content (1)";

		assert mailBox.get("Christopher Nolan").equals(
			Arrays.asList(
				"����, ������ ��� ��� ������ ������ ����, ����� ����������� ��� �������� ������� �. ��� �� ������!",
				"� ��� � �� ����� ������������."
			)
		) : "wrong mailService mailbox content (2)";

		assert mailBox.get(randomTo).equals(Collections.<String>emptyList()) : "wrong mailService mailbox content (3)";


		// �������� ������ �� ���� �������.
		Salary salary1 = new Salary("Facebook", "Mark Zuckerberg", 1);
		Salary salary2 = new Salary("FC Barcelona", "Lionel Messi", Integer.MAX_VALUE);
		Salary salary3 = new Salary(randomFrom, randomTo, randomSalary);

		// �������� ��������� �������, ��������������� ��������.
		MailService<Integer> salaryService = new MailService<>();

// ��������� ������ ������� �������� ��������
		Arrays.asList(salary1, salary2, salary3).forEach(salaryService);

		// ��������� � �������� ������� "��������� �����",
//   ��� �� ���������� ����� �������� ������ �������, ������� ���� ��� ����������.
		Map<String, List<Integer>> salaries = salaryService.getMailBox();
		assert salaries.get(salary1.getTo()).equals(Arrays.asList(1)) : "wrong salaries mailbox content (1)";
		assert salaries.get(salary2.getTo()).equals(Arrays.asList(Integer.MAX_VALUE)) : "wrong salaries mailbox content (2)";
		assert salaries.get(randomTo).equals(Arrays.asList(randomSalary)) : "wrong salaries mailbox content (3)";
	}

	public static class MailService<T> implements Consumer<Mail<T>> {
		private MyMap<T> mailBox = new MyMap<>();

		@Override
		public void accept(Mail<T> mailMessage) {
			List<T> list = mailBox.get(mailMessage.to);
			if (list.isEmpty()) {
				list = new ArrayList<>();
				mailBox.put(mailMessage.getTo(), list);
			}
			list.add(mailMessage.getContent());
		}

		public MyMap<T> getMailBox() {
			return mailBox;
		}
	}

	public static abstract class  Mail<T> {
		private final String from;
		private final String to;
		private final T content;

		protected Mail(String from, String to, T content) {
			this.from = from;
			this.to = to;
			this.content = content;
		}

		public String getFrom() {
			return from;
		}

		public String getTo() {
			return to;
		}

		public T getContent() {
			return content;
		}
	}

	public static class MailMessage extends Mail<String>{

		 protected MailMessage(String from, String to, String content) {
			 super(from, to, content);
		 }
	 }

	public static class Salary extends Mail<Integer>{

		protected Salary(String from, String to, Integer content) {
			super(from, to, content);
		}
	}

	public static class MyMap<T>  extends HashMap<String, List<T>> {

		@Override
		public List<T> get(Object key) {
			List<T> result = super.get(key);
			if (result == null)
				return Collections.<T>emptyList();
			return result;
		}
	}


}

